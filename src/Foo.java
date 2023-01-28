public interface Foo {
    public void recordSuccess();
    public void recordFailure(String response);
    public String getState();
    //public void setState(State state);
    public String attemptRequest();
}
