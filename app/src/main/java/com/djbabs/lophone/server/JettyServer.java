package com.djbabs.lophone.server;


import android.app.Activity;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

public class JettyServer {

    private Server server;
    private static int SERVER_PORT = 6060;
    public static Activity activity;

    public JettyServer(Activity activity){
        this.activity = activity;
    }

    public void start() throws Exception {
        server = new Server(SERVER_PORT);
        ServletHandler handler = new ServletHandler();
        //1.Creating the resource handler
        ResourceHandler resourceHandler= new ResourceHandler();

        //2.Setting Resource Base
        resourceHandler.setResourceBase("storage/emulated/0/");


        //3.Enabling Directory Listing
//        resourceHandler.setDirectoriesListed(true);

        //4.Setting Context Source
//        ContextHandler contextHandler= new ContextHandler("/jcg");

        //5.Attaching Handlers
//        contextHandler.setHandler(resourceHandler);
//        handler.
        resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
        handler.setHandler(resourceHandler);

        handler.addServletWithMapping(BlockingServelet.class, "/index");
        server.setHandler(handler);
        server.start();
    }

    public void stop() throws Exception {
        if(server != null)
        if(server.isRunning()){
            server.stop();
        }
    }
}


