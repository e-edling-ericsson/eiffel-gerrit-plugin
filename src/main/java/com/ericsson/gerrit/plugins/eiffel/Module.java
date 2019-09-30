/*
   Copyright 2019 Ericsson AB.
   For a full list of individual contributors, please see the commit history.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.ericsson.gerrit.plugins.eiffel;

import com.ericsson.gerrit.plugins.eiffel.handlers.MessageQueueHandler;
import com.ericsson.gerrit.plugins.eiffel.listeners.GerritEventListener;
import com.google.gerrit.common.EventListener;
import com.google.gerrit.extensions.events.LifecycleListener;
import com.google.gerrit.extensions.registration.DynamicSet;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.internal.UniqueAnnotations;

/**
 * This class is needed by Gerrit to load the plug-in and must be named Module AbstractModule
 * implementation in order to register all extensions.
 *
 */
public class Module extends AbstractModule {

    @Override
    @CoberturaIgnore
    protected void configure() {
        bind(MessageQueueHandler.class).in(Scopes.SINGLETON);
        bind(LifecycleListener.class).annotatedWith(UniqueAnnotations.create())
                                     .to(MessageQueueHandler.class);

        // Example of how to register plugin configuration to the project screen
        //bind(ProjectConfigEntry.class).annotatedWith(
        //        Exports.named(EiffelPluginConfiguration.ENABLED))
        //                              .toInstance(new ProjectConfigEntry("Enable Eiffel messaging",
        //                                      false));

        // Register change listener that will send messages
        DynamicSet.bind(binder(), EventListener.class).to(GerritEventListener.class);

        // Example how to define a Send test message button
        //install(new RestApiModule() {
        //    @Override
        //    @CoberturaIgnore
        //    protected void configure() {
        //        post(PROJECT_KIND, "eiffel-test-message").to(EiffelTestMessageSender.class);
        //    }
        //});
    }
}
