package com.ardecs.springbootapp.server;

import java.util.List;

import com.ardecs.springbootapp.client.UserService;
import com.ardecs.springbootapp.entities.User;
import com.ardecs.springbootapp.repositories.UserRepository;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends RemoteServiceServlet implements UserService {


    @Autowired
    private UserRepository repository;

    @Override
    public List<User> list() {
        return repository.findAll();
    }

    @Override
    public void delete(User user) {
        repository.delete(user.getId());
    }

    @Override
    public User save(User data) {
        User user = new User();
        user.setId(data.getId());
        user.setLogin(data.getLogin());
        user.setPassword(data.getPassword());
        repository.save(user);
        data.setId(user.getId());
        return data;
    }

    public String testMethod(){
        return "Hello, user!";
    }

//    @Override
//    protected SerializationPolicy doGetSerializationPolicy(HttpServletRequest request, String moduleBaseURL, String strongName) {
//
//            // The request can tell you the path of the web app relative to the
//            // container root.
//            String contextPath = request.getContextPath();
//
//            String modulePath = null;
//            if (moduleBaseURL != null) {
//                try {
//                    modulePath = new URL(moduleBaseURL).getPath();
//                } catch (MalformedURLException ex) {
//                    // log the information, we will default
//                    this.log("Malformed moduleBaseURL: " + moduleBaseURL, ex);
//                }
//            }
//            SerializationPolicy serializationPolicy = null;
//
//            /*
//             * Check that the module path must be in the same web app as the servlet
//             * itself. If you need to implement a scheme different than this, override
//             * this method.
//             */
//            if (modulePath == null || !modulePath.startsWith(contextPath)) {
//                String message = "ERROR: The module path requested, "
//                        + modulePath
//                        + ", is not in the same web application as this servlet, "
//                        + contextPath
//                        + ".  Your module may not be properly configured or your client and server code maybe out of date.";
//                this.log(message);
//            } else {
//                // Strip off the context path from the module base URL. It should be a
//                // strict prefix.
//                String contextRelativePath = modulePath.substring(contextPath.length());
//
//                String serializationPolicyFilePath = SerializationPolicyLoader.getSerializationPolicyFileName(contextRelativePath
//                        + strongName);
//                // Open the RPC resource file and read its contents.
//                InputStream is =  this.getClass().getClassLoader().getResourceAsStream(serializationPolicyFilePath);
//
//                try {
//                    if (is != null) {
//                        try {
//                            serializationPolicy = SerializationPolicyLoader.loadFromStream(is,
//                                    null);
//                        } catch (ParseException e) {
//                            this.log("ERROR: Failed to parse the policy file '"
//                                    + serializationPolicyFilePath + "'", e);
//                        } catch (IOException e) {
//                            this.log("ERROR: Could not read the policy file '"
//                                    + serializationPolicyFilePath + "'", e);
//                        }
//                    } else {
//                        String message = "ERROR: The serialization policy file '"
//                                + serializationPolicyFilePath
//                                + "' was not found; did you forget to include it in this deployment?";
//                        this.log(message);
//                    }
//                } finally {
//                    if (is != null) {
//                        try {
//                            is.close();
//                        } catch (IOException e) {
//                            // Ignore this error
//                        }
//                    }
//                }
//            }
//
//            return serializationPolicy;
//    }
}
