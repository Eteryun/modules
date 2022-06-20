package com.eteryun.scale;

import com.eteryun.api.module.IModuleManager;
import com.eteryun.api.module.Module;
import com.eteryun.api.module.ModuleConfig;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class ScaleModule extends Module {
    public ScaleModule(IModuleManager moduleManager, Logger logger, ModuleConfig config, Path path) {
        super(moduleManager, logger, config, path);
    }
}
