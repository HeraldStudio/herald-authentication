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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class AuthenticationServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(
            AuthenticationServlet.class.getName());
    private String configLocation;
    private AuthenticationService authenticationService;

    @Override
    public void init(ServletConfig config) {
        configLocation = config.getInitParameter("configLocation");
        String msg = "config location: " + configLocation;
        LOGGER.log(Level.INFO, msg);
        authenticationService = new AuthenticationServiceImpl(configLocation);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null) {
            response.sendError(400, "username and password are required");
            return;
        }

        try {
            StudentUser studentUser =
                    authenticationService.authenticate(username, password);
            CommaSeparatedValue csv = new CommaSeparatedValue();
            csv.append(studentUser.getFullName());
            csv.append(studentUser.getCardNumber());

            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.print(csv.toString());
            out.close();
        } catch (AuthenticationException ex) {
            response.sendError(401, "Not Authorized");
        }
    }
}
