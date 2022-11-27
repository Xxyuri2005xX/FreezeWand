package it.xxyuri2005xx.freezewand;

import it.xxyuri2005xx.freezewand.Listeners.FreezeListener;
import it.xxyuri2005xx.freezewand.Util.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public PluginManager manager = Bukkit.getPluginManager();

    @Override
    public void onEnable() {
        instance = this;
        new ServerUtil();
        manager.registerEvents(new FreezeListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
