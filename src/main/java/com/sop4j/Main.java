/*
 *    Copyright 2013 William R. Speirs
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.sop4j;

import java.io.StringWriter;
import java.util.Arrays;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class Main {

    public static void main(String[] args) {
        // create the velocity engine
        final VelocityEngine velocity = new VelocityEngine();

        // set the logger to our Log4J root logger
        velocity.setProperty("runtime.log.logsystem.log4j.logger", "root");
        
        // use the resource loader as it's easy with Maven's path setup
        velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        // make sure to call init before using the engine
        velocity.init();

        // create the context to set all the variables
        final VelocityContext context = new VelocityContext();

        // setup the context
        context.put("user", new UserBean("Bill"));
        context.put("site", "www.sop4j.com");
        context.put("articles", Arrays.asList(new ArticleBean("Welcome"),
                                              new ArticleBean("Parsing the Command Line"),
                                              new ArticleBean("Document Templating or Mail Merge")));

        // get the template
        final Template template = velocity.getTemplate("my_template.vm");
        final StringWriter sw = new StringWriter();
        
        // merge the template and context
        template.merge(context, sw);

        // print out the resulting document
        System.out.println(sw.toString());
    }
    
    public static class UserBean {
        private final String name;
        
        public UserBean(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    
    public static class ArticleBean {
        private final String title;
        
        public ArticleBean(final String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
        
        @Override
        public String toString() {
            return getTitle();
        }
    }

}
