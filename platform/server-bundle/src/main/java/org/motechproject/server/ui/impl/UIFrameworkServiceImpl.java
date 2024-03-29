package org.motechproject.server.ui.impl;

import org.apache.commons.lang.StringUtils;
import org.motechproject.osgi.web.ModuleRegistrationData;
import org.motechproject.osgi.web.UIFrameworkService;
import org.motechproject.server.ui.ex.AlreadyRegisteredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service("uiFrameworkService")
public class UIFrameworkServiceImpl implements UIFrameworkService {

    private static final Logger LOG = LoggerFactory.getLogger(UIFrameworkServiceImpl.class);

    private Map<String, ModuleRegistrationData> individuals = new HashMap<>();
    private Map<String, ModuleRegistrationData> links = new HashMap<>();
    private Map<String, ModuleRegistrationData> withoutUI = new HashMap<>();

    @Override
    public void registerModule(ModuleRegistrationData module) {
        String moduleName = module.getModuleName();

        if (links.containsKey(moduleName) || individuals.containsKey(moduleName) || withoutUI.containsKey(moduleName)) {
            throw new AlreadyRegisteredException("Module already registered");
        }

        if (StringUtils.isNotBlank(module.getUrl())){
            if (module.getSubMenu().isEmpty()) {
                links.put(moduleName, module);
            } else {
                individuals.put(moduleName, module);
            }
        } else {
            withoutUI.put(moduleName, module);
        }

        LOG.debug(String.format("Module %s registered in UI framework", module.getModuleName()));
    }

    @Override
    public void unregisterModule(String moduleName) {
        if (links.containsKey(moduleName)) {
            links.remove(moduleName);
        }

        if (individuals.containsKey(moduleName)) {
            individuals.remove(moduleName);
        }

        LOG.debug(String.format("Module %s unregistered from UI framework", moduleName));
    }

    @Override
    public Map<String, Collection<ModuleRegistrationData>> getRegisteredModules() {
        Map<String, Collection<ModuleRegistrationData>> map = new HashMap<>(2);
        map.put(MODULES_WITH_SUBMENU, individuals.values());
        map.put(MODULES_WITHOUT_SUBMENU, links.values());
        map.put(MODULES_WITHOUT_UI, withoutUI.values());

        return map;
    }

    @Override
    public ModuleRegistrationData getModuleData(String moduleName) {
        ModuleRegistrationData module = null;

        if (links.containsKey(moduleName)) {
            module = links.get(moduleName);
        } else if (individuals.containsKey(moduleName)) {
            module = individuals.get(moduleName);
        } else if (withoutUI.containsKey(moduleName)) {
            module = withoutUI.get(moduleName);
        }

        return module;
    }

    @Override
    public boolean isModuleRegistered(String moduleName) {
        return getModuleData(moduleName) != null;
    }
}
