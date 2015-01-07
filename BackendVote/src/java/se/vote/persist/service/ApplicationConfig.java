/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.persist.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Teddy
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        System.out.println("JUST CHECKING IF SOMETHING HAPPENS HERE___________");
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(se.vote.persist.service.PollRest.class);
        resources.add(se.vote.persist.service.UserRest.class);
    }
    
}
