package me.sorroko.vortex;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

public class VortexManager {
	AirPortal plugin;
	public VortexManager(AirPortal instance){
		plugin=instance;
	}
private HashMap<String,Vortex> vortexii=new HashMap<String,Vortex>();
public YamlConfiguration vortexConf;

public Vortex getVortex(String name){
	return vortexii.get(name);
}

public Set<Entry<String,Vortex>> getVortexEntries(){
	return vortexii.entrySet();
}

public void setVortex(String name,Vortex v){
	vortexii.put(name, v);
}

public boolean vortexExists(String name){
	return vortexii.containsKey(name);
}

public void removeVortex(String name){
	vortexii.remove(name);
}

public void loadVortexii(){
ConfigUtil.loadConfig("vortex_locs");
vortexConf = ConfigUtil.getConfig("vortex_locs");
	for(String k:vortexConf.getKeys(false)){
		vortexii.put(k, new Vortex(vortexConf.getConfigurationSection(k)));
	}
}

public void saveConfig(){
	for(String k:vortexii.keySet()){
		vortexConf.createSection(k);
		vortexConf.set(k, vortexii.get(k).save(vortexConf.getConfigurationSection(k)));
	}
	ConfigUtil.saveConfig(vortexConf, "configName");
		
}

}