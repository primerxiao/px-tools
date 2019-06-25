package pxt.service;

import common.service.CommonService;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.stage.Stage;
import pxt.gui.main.MainController;

import java.io.IOException;

/**
 * @author primerxiao
 * @date 2019/6/9
 */
public interface MainService {

    void init() throws FlowException;
   }
