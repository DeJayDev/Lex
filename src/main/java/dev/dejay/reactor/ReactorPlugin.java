package dev.dejay.reactor;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import java.nio.file.Path;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class ReactorPlugin {

    public final static String VERSION = "0.0.1";

    private class PaperPlugin extends JavaPlugin {
        @Override
        public void onEnable() {
            getServer().getLogger().severe("Reactor Version " + VERSION + " is Running...");
        }
    }

    @Plugin(id="reactor", name = "Reactor", version = VERSION, authors = "DeJayDev")
    private class VelocityPlugin {
        private final ProxyServer proxy;
        private final Logger logger;
        private final Path dataDirectory;

        @Inject
        public VelocityPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
            this.proxy = server;
            this.logger = logger;
            this.dataDirectory = dataDirectory;
            logger.severe("Reactor Version " + VERSION + " is Running...");
        }
    }

    private class WaterfallPlugin extends net.md_5.bungee.api.plugin.Plugin {
        @Override
        public void onEnable() {
            getLogger().severe("Reactor Version " + VERSION + " is Running...");
        }
    }

    /**
     * Runs the Reactor as a Paper plugin
     * @deprecated Why are you here?
     */
    @Deprecated
    public void runPlugin(Server server) {
        server.getPluginManager().enablePlugin(new PaperPlugin());
    }

}
