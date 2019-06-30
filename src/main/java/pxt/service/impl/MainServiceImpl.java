package pxt.service.impl;

import common.container.PxtContainer;
import common.service.CommonService;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.util.VetoException;
import lombok.extern.log4j.Log4j2;
import mapper.MenuItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxt.gui.main.MainController;
import pxt.gui.uicomponents.EsbToJavaFileController;
import pxt.gui.uicomponents.NavigationController;
import pxt.service.MainService;

/**
 * @author primerxiao
 * @date 2019/6/9
 */
@Log4j2
@Service
public class MainServiceImpl extends CommonService<MainController> implements MainService {

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Override
    public void init() throws FlowException {

        Flow innerFlow = new Flow(NavigationController.class);
        final FlowHandler flowHandler = innerFlow.createHandler(this.controller.getContext());
        this.controller.getContext().register(PxtContainer.Constant.CONTENT_FLOW_HANDLER, flowHandler);
        this.controller.getContext().register(PxtContainer.Constant.CONTENT_FLOW, innerFlow);
        this.controller.getDrawer().setContent(flowHandler.start(new DefaultFlowContainer()));
        this.controller.getContext().register(PxtContainer.Constant.CONTENT_PANE, this.controller.getDrawer().getContent().get(0));
        Flow contentFlow = (Flow) this.controller.getContext().getRegisteredObject(PxtContainer.Constant.CONTENT_FLOW);
        contentFlow.withGlobalLink("esbCodeHelper", EsbToJavaFileController.class);

        this.controller.getEsbCodeHelper().setOnAction(event -> {
            try {
                flowHandler.handle("esbCodeHelper");
            } catch (VetoException e) {
                e.printStackTrace();
            } catch (FlowException e) {
                e.printStackTrace();
            }
        });

    }



}
