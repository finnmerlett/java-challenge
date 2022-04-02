package io.carbonchain.hiring.java;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Core {

  private final HashMap<String, Middleware[]> middlewares;
  private final HashMap<String, Controller> controllers;

  public Core(HashMap<String, Middleware[]> middlewares, HashMap<String, Controller> controllers) {
    this.middlewares = middlewares;
    this.controllers = controllers;
  }

  public String run(String path, String endpoint, String[] params) {
    try {
      Request request = applyMiddleware(endpoint, new Request(params));
      return dispatchController(path, endpoint, request);
    } catch (ApplicationException e) {
      return "Problem when processing request: " + e.getMessage();
    }
  }

  private Request applyMiddleware(String endpoint, Request request) {
    Middleware[] endpointMiddlewares = middlewares.get(endpoint);
    if (endpointMiddlewares == null) {
      return request;
    }
    for (Middleware middleware : endpointMiddlewares) {
      request = middleware.handle(request);
    }
    return request;
  }

  private String dispatchController(
      String path,
      String endpoint,
      Request request
  ) throws ApplicationException {
    Controller controller = controllers.get(path);
    if (controller == null) {
      throw new ApplicationException("Path " + path + " has no matching controller");
    }
    return invokeEndpoint(endpoint, controller, request);
  }

  private String invokeEndpoint(
      String endpoint,
      Controller controller,
      Request request
  ) throws ApplicationException {
    try {
      return (String) controller.getClass()
          .getDeclaredMethod(endpoint, Request.class)
          .invoke(controller, request);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new ApplicationException("Problem when calling endpoint " + endpoint + ": " + e.getCause());
    }
  }
}
