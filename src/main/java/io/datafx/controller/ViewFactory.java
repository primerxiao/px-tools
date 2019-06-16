/**
 * Copyright (c) 2011, 2014, Jonathan Giles, Johan Vos, Hendrik Ebbers
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of DataFX, the website javafxdata.org, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DATAFX BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package io.datafx.controller;

import common.container.PxtContainer;
import io.datafx.controller.context.ViewContext;
import io.datafx.controller.context.ViewMetadata;
import io.datafx.controller.context.event.ContextPostConstructListener;
import io.datafx.controller.util.NullNode;
import io.datafx.core.DataFXUtils;
import io.datafx.core.ExceptionHandler;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pxt.MainApplication;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * This class contains static methods to create views in the DataFX Container context.
 * All views that will use DataFX controller or Flow features have to be created by this class.
 * Normally a developer doesn't need to use this class because the Flow API will
 * call the methods internally whenever a new view is created in a flow.
 * If a developers doesn't want to use the flow API and only want to create a
 * single view that uses DataFX controller features this class can be used to create the view.
 */
@Log4j2
public class ViewFactory {

    private static io.datafx.controller.ViewFactory instance;

    private ViewFactory() {
    }

    /**
     * Returns the single instance of the ViewFactory class (singleton pattern)
     *
     * @return the ViewFactory singleton
     */
    public static synchronized io.datafx.controller.ViewFactory getInstance() {
        if (instance == null) {
            instance = new io.datafx.controller.ViewFactory();
        }
        return instance;
    }

    /**
     * Creates a new MVC based view by using the given controller class. The
     * class needs a default constructor (no parameters) and a
     * {@link ViewController} annotation to link to the fxml file. You can skip
     * the annotation if you want to use the controller API conventions. By
     * doing so the fxml files has to be in the package as the controller and
     * must fit to a naming convention (see {@link ViewController} for more
     * informations). The method returns a {@link io.datafx.controller.context.ViewContext}. This is a
     * wrapper around the view (view-node and controller) and can be used to
     * register your datamodel to the view. The doc of {@link io.datafx.controller.context.ViewContext} will
     * provide more information about this topic.
     *
     * @param controllerClass the class of the controller.
     * @return a ViewContext that encapsulate the complete MVC
     * @throws FxmlLoadException if the fxml file can not be loaded
     */
    public <T> ViewContext<T> createByController(final Class<T> controllerClass)
            throws FxmlLoadException {
        return createByController(controllerClass, null);
    }

    /**
     * Creates a new MVC based view by using the given controller class. The
     * class needs a default constructor (no parameters) and a
     * {@link ViewController} annotation to link to the fxml file. You can skip
     * the annotation if you want to use the controller API conventions. By
     * doing so the fxml files has to be in the package as the controller and
     * must fit to a naming convention (see {@link ViewController} for more
     * informations). The method returns a {@link io.datafx.controller.context.ViewContext}. This is a
     * wrapper around the view (view-node and controller) and can be used to
     * register your datamodel to the view. The doc of {@link io.datafx.controller.context.ViewContext} will
     * provide more information about this topic. By using this method you can
     * overwrite the path to your fxml file.
     *
     * @param controllerClass the class of the controller.
     * @param fxmlName        path to the fxml file that will be used for the generated MVC
     *                        context
     * @return a ViewContext that encapsulate the complete MVC
     * @throws FxmlLoadException if the fxml file can not be loaded
     */
    public <T> ViewContext<T> createByController(
            final Class<T> controllerClass, String fxmlName)
            throws FxmlLoadException {
        return createByController(controllerClass, fxmlName, new ViewConfiguration());
    }

    public static ApplicationContext applicationContext;

    /**
     * Creates a new MVC based view by using the given controller class.
     * The class needs a default constructor (no parameters) and
     * a {@link ViewController} annotation to link to the fxml file.
     * You can skip the annotation if you want to use the controller API conventions.
     * By doing so the fxml files has to be in the package as the controller and
     * must fit to a naming convention (see {@link ViewController}
     * for more information). The method returns a
     * {@link io.datafx.controller.context.ViewContext}.
     * This is a wrapper around the view (view-node and controller) and can be
     * used to register your datamodel to the view. The doc of
     * {@link io.datafx.controller.context.ViewContext} will
     * provide more information about this topic. By using this method you can
     * overwrite the path to your fxml file.
     *
     * @param <T>
     * @param controllerClass
     * @param fxmlName
     * @param viewConfiguration    The configuration for the view
     * @param viewContextResources initial resources that will be added to the view context
     * @return
     * @throws io.datafx.controller.FxmlLoadException
     */
    public <T> ViewContext<T> createByController(
            final Class<T> controllerClass, String fxmlName, ViewConfiguration viewConfiguration, Object... viewContextResources)
            throws FxmlLoadException {
        try {
            // 1. Create an instance of the Controller
            final T controller = controllerClass.newInstance();
            ViewMetadata metadata = new ViewMetadata();
            ViewController controllerAnnotation = (ViewController) controllerClass
                    .getAnnotation(ViewController.class);
            if (controllerAnnotation != null && !controllerAnnotation.title().isEmpty()) {
                metadata.setTitle(controllerAnnotation.title());
            }
            if (controllerAnnotation != null && !controllerAnnotation.iconPath().isEmpty()) {
                metadata.setGraphic(new ImageView(controllerClass.getResource(controllerAnnotation.iconPath()).toExternalForm()));
            }


            // 2. Create the view and make sure the @FXML annotations are injected
            Node viewNode = null;
            if (controllerAnnotation != null && controllerAnnotation.root() != null && !controllerAnnotation.root().equals(NullNode.class)) {
                viewNode = controllerAnnotation.root().newInstance();
                injectNodeIDs(viewNode);
            } else {
                FXMLLoader loader = createLoader(controller, fxmlName, viewConfiguration);
                viewNode = (Node) loader.load();
            }
            injectFXMLNodes(controller, viewNode);

            // 3. create the context
            ViewContext<T> context = new ViewContext<>(viewNode,
                    controller, metadata, viewConfiguration, viewContextResources);


            // 4. Resolve the @Inject points in the Controller and call
            // @PostConstruct
            context.getResolver().injectResources(controller);

            // 5. Call listeners
            ServiceLoader<ContextPostConstructListener> postConstructLoader = ServiceLoader.load(ContextPostConstructListener.class);
            for (ContextPostConstructListener listener : postConstructLoader) {
                listener.postConstruct(context);
            }

            // 6. call PostConstruct methods
            for (final Method method : DataFXUtils.getInheritedDeclaredMethods(controller.getClass())) {
                if (method.isAnnotationPresent(PostConstruct.class)) {
                    DataFXUtils.callPrivileged(method, controller);
                }
            }
            //7.注入spring bean
            Field[] declaredFields = controller.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Autowired annotation = declaredField.getAnnotation(Autowired.class);
                if (Objects.isNull(annotation)) {
                    continue;
                }
                Class<?> type = declaredField.getType();
                Thread thread = Thread.currentThread();
                System.out.println(MainApplication.applicationContext);
                //注入
                Object bean = applicationContext.getBean(type);
                declaredField.set(controller,bean);
            }
            return context;
        } catch (Exception e) {
            throw new FxmlLoadException("Can't create controller for class " + controllerClass, e);
        }
    }

    /**
     * Helper method that will show exactly one view in a stage.
     * This method can be used to create a simple one view application or test a view by only some lines of code
     *
     * @param stage           The stage in that the view will be shown
     * @param controllerClass the class of the controller that defines the view
     */
    public void showInStage(Stage stage, Class<?> controllerClass) throws FxmlLoadException {
        Scene myScene = new Scene((Parent) createByController(controllerClass).getRootNode());
        stage.setScene(myScene);
        stage.show();
    }

    private FXMLLoader createLoader(final Object controller, String fxmlName, ViewConfiguration viewConfiguration)
            throws FxmlLoadException {
        Class<?> controllerClass = controller.getClass();
        String foundFxmlName = getFxmlName(controllerClass);
        if (fxmlName != null) {
            foundFxmlName = fxmlName;
        }
        if (foundFxmlName == null) {
            throw new FxmlLoadException("No FXML File specified!");
        }

        URL fxmlUrl = controllerClass.getResource(foundFxmlName);

        if (fxmlUrl == null) {
            throw new FxmlLoadException("Can't find FXML file for controller " + controller.getClass());
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
        fxmlLoader.setBuilderFactory(viewConfiguration.getBuilderFactory());
        fxmlLoader.setCharset(viewConfiguration.getCharset());
        fxmlLoader.setResources(viewConfiguration.getResources());
        fxmlLoader.setController(controller);
        fxmlLoader.setControllerFactory(c -> controller);
        return fxmlLoader;
    }

    private String getFxmlName(Class<?> controllerClass) {
        String foundFxmlName = null;

        if (controllerClass.getSimpleName().endsWith("Controller")) {
            String nameByController = controllerClass.getSimpleName()
                    .substring(
                            0,
                            controllerClass.getSimpleName().length()
                                    - "Controller".length())
                    + ".fxml";
            if (DataFXUtils.canAccess(controllerClass, nameByController)) {
                foundFxmlName = nameByController;
            }
        }

        ViewController controllerAnnotation = controllerClass
                .getAnnotation(ViewController.class);
        if (controllerAnnotation != null && !controllerAnnotation.value().isEmpty()) {
            foundFxmlName = controllerAnnotation.value();
        }
        return foundFxmlName;
    }


    /**
     * This methods creates a tab that contains the given view.
     * By doing so the metadata of the view will be bound to the tab properties.
     * So the text and icon of the tab can be changed by the view.
     *
     * @param controllerClass the class that defines the controller of the view
     */
    public <T> Tab createTab(Class<T> controllerClass) throws FxmlLoadException {
        return createTab(createByController(controllerClass));
    }

    /**
     * This methods creates a tab that contains the given view.
     * By doing so the metadata of the view will be bound to the tab properties.
     * So the text and icon of the tab can be changed by the view.
     *
     * @param controllerClass  the class that defines the controller of the view
     * @param exceptionHandler the exception handle for the view. This handler will handle all exceptions that will be thrown in the DataFX container context
     */
    public <T> Tab createTab(Class<T> controllerClass, ExceptionHandler exceptionHandler) throws FxmlLoadException {
        return createTab(createByController(controllerClass), exceptionHandler);
    }

    /**
     * This methods creates a tab that contains the given view.
     * By doing so the metadata of the view will be bound to the tab properties.
     * So the text and icon of the tab can be changed by the view.
     *
     * @param context the context of the view. This includes the view and its controller instance
     *                so that all needed informations are part of the context
     */
    public <T> Tab createTab(ViewContext<T> context) {
        return createTab(context, ExceptionHandler.getDefaultInstance());
    }

    /**
     * This methods creates a tab that contains the given view.
     * By doing so the metadata of the view will be bound to the tab properties.
     * So the text and icon of the tab can be changed by the view.
     *
     * @param context          the context of the view. This includes the view and its controller instance
     *                         so that all needed informations are part of the context
     * @param exceptionHandler the exception handle for the view. This handler will handle all exceptions that will be thrown in the DataFX container context
     */
    public <T> Tab createTab(ViewContext<T> context, ExceptionHandler exceptionHandler) {
        Tab tab = new Tab();
        tab.textProperty().bind(context.getMetadata().titleProperty());
        tab.graphicProperty().bind(context.getMetadata().graphicsProperty());
        tab.setOnClosed(e -> {
            try {
                context.destroy();
            } catch (Exception exception) {
                exceptionHandler.setException(exception);
            }
        });
        tab.setContent(context.getRootNode());
        return tab;
    }

    /**
     * Because of some restrictions in the FXMLLoader not all fields that are annotated with @FXML will be injected in
     * a controller when using the FXMLLoader class. This helper methods injects all fields that were not injected by
     * JavaFX basics.
     * The following types will not be injected by FXMLLoader:
     * - private fields in a superclass of the controller class
     * - fields that defines a node that is part of a sub-fxml. This is a fxml definition that is included in the fxml
     * file.
     *
     * @param controller The controller instance
     * @param root       The root Node
     * @param <T>        Type of the controller
     */
    private <T> void injectFXMLNodes(T controller, Node root) {

        List<Field> fields = DataFXUtils.getInheritedDeclaredFields(controller.getClass());
        for (Field field : fields) {
            if (field.getAnnotation(FXML.class) != null) {
                if (DataFXUtils.getPrivileged(field, controller) == null) {
                    if (Node.class.isAssignableFrom(field.getType())) {
                        Node toInject = root.lookup("#" + field.getName());
                        if (toInject != null) {
                            DataFXUtils.setPrivileged(field, controller, toInject);
                        }
                    }
                }
            }

            if (field.getAnnotation(ViewNode.class) != null) {
                String id = field.getName();
                if (field.getAnnotation(ViewNode.class).value() != null && !field.getAnnotation(ViewNode.class).value().isEmpty()) {
                    id = field.getAnnotation(ViewNode.class).value();
                }
                if (DataFXUtils.getPrivileged(field, controller) == null) {
                    if (Node.class.isAssignableFrom(field.getType())) {
                        Node toInject = root.lookup("#" + id);
                        if (toInject != null) {
                            DataFXUtils.setPrivileged(field, controller, toInject);
                        }
                    }
                }
            }
        }

    }

    private void injectNodeIDs(Node root) {
        List<Field> fields = DataFXUtils.getInheritedDeclaredFields(root.getClass());
        for (Field field : fields) {
            if (Node.class.isAssignableFrom(field.getType()) && field.getAnnotation(ViewNode.class) != null) {
                String id = field.getName();
                if (field.getAnnotation(ViewNode.class).value() != null && !field.getAnnotation(ViewNode.class).value().isEmpty()) {
                    id = field.getAnnotation(ViewNode.class).value();
                }

                Node child = DataFXUtils.getPrivileged(field, root);
                child.setId(id);
            }
        }
    }

    private <T> void injectProperties(T controller, Node root) {
        List<Field> fields = DataFXUtils.getInheritedDeclaredFields(controller.getClass());
        for (Field field : fields) {
            if (field.getAnnotation(ViewProperty.class) != null && ObservableValue.class.isAssignableFrom(field.getType())) {
                String id = field.getAnnotation(ViewProperty.class).value();
                String[] splitted = id.split(".");
                Node baseNode = root.lookup("#" + splitted[0]);
                Object foundProperty = null;

                ObservableValue toInject = DataFXUtils.getPrivileged(field, controller);
                if (toInject == null) {
                    toInject = new SimpleObjectProperty<>();
                    DataFXUtils.setPrivileged(field, controller, toInject);
                }

                if(Property.class.isAssignableFrom(field.getType())) {
                    if(foundProperty instanceof Property) {
                        ((Property)toInject).bindBidirectional((Property) foundProperty);
                    } else if(foundProperty instanceof ObservableValue) {
                        ((Property)toInject).bind((ObservableValue) foundProperty);
                    }
                } else if(foundProperty instanceof Property) {
                    ((Property)toInject).bind((ObservableValue) foundProperty);
                }
            }
        }
    }
}
