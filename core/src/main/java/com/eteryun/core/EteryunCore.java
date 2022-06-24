package com.eteryun.core;

import com.eteryun.api.module.Module;
import com.eteryun.api.module.ModuleConfig;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class EteryunCore extends Module {
    private static EteryunCore instance;

    public EteryunCore(Logger logger, ModuleConfig config, Path path) {
        super(logger, config, path);
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    public static EteryunCore getInstance() {
        return instance;
    }
}
