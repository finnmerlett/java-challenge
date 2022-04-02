package io.carbonchain.hiring.java;

public class Request {

  private String[] params;

  public Request(String[] params) {
    this.params = params;
  }

  public String get(int position) {
    return params[position];
  }
}
