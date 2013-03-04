/*
 * Copyright 2013 Herald Studio, Southeast University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.seu.herald.authentication;

import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = Logger.getLogger(
            AuthenticationServiceImpl.class.getName());
    private IdentityFactory factory;

    public AuthenticationServiceImpl(String configLocation) {
        try {
            String userHome = System.getProperty("user.home");
            factory = IdentityFactory.createFactory(userHome + configLocation);
            LOGGER.log(Level.INFO, "IdentityFactory constructed");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException("fail to create IdentityFactory");
        }
    }

    @Override
    public StudentUser authenticate(String cardNumber, String password)
            throws AuthenticationException {
        IdentityManager im = factory.getIdentityManager();
        boolean pass = im.checkPassword(cardNumber, password);
        if (!pass) {
            String notPass = new StringBuilder()
                    .append("not authenticated: ")
                    .append(cardNumber)
                    .toString();
            LOGGER.log(Level.INFO, notPass);
            throw new AuthenticationException(cardNumber);
        }

        String msg = new StringBuilder()
                .append("authenticated: ")
                .append(cardNumber)
                .toString();
        String fullName = im.getUserNameByID(cardNumber);
        StudentUser studentUser = new StudentUser(cardNumber, fullName);
        return studentUser;
    }
}
