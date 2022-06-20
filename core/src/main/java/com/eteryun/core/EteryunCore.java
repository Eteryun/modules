package com.eteryun.core;

import com.eteryun.api.module.IModuleManager;
import com.eteryun.api.module.Module;
import com.eteryun.api.module.ModuleConfig;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class EteryunCore extends Module {
    private static EteryunCore instance;

    public EteryunCore(IModuleManager moduleManager, Logger logger, ModuleConfig config, Path path) {
        super(moduleManager, logger, config, path);
        instance = this;
    }

    public static EteryunCore getInstance() {
        return instance;
    }
}
