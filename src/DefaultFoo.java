public class DefaultFoo implements Foo {
    private final long timeout;
    private final long retryTimePeriod;
    private final WebService service;
    long lastFailureTime;
    private String lastFailureResponse;
    int failureCount;
    private final long failureThreshold;
    private State state;
    private final long futureTimePeriod= 1000000000;

    public DefaultFoo( WebService service, long timeout, int failureThreshold, long retryTimePeriod) {
        this.service= service;
        this.timeout = timeout;
        //begin with Closed State. Assume all good in the beginning
        this.state= State.CLOSED;
        this.failureThreshold= failureThreshold;
        //After this time period we will attempt a try to check if service is up or not
        this.retryTimePeriod= retryTimePeriod;
        //An huge amount of time in future which basically indicates the last failure never happened
        this.lastFailureTime= System.currentTimeMillis()+futureTimePeriod;
        this.failureCount=0;
    }
    //Reset everything to Default
    @Override
    public void recordSuccess() {
        this.failureCount=0;
        this.lastFailureTime= System.currentTimeMillis()+futureTimePeriod;
        this.state= State.CLOSED;
    }

    @Override
    public void recordFailure(String response) {
        failureCount++;
        this.lastFailureTime= System.currentTimeMillis();
        this.lastFailureResponse= response;
    }

    @Override
    public String getState() {
        evaluateState();
        return state.name();
    }

    @Override
    public String attemptRequest() {
        evaluateState();
        if(state== State.OPEN){
            return this.lastFailureResponse;
        }
        else{
            //Make API req.
            var response= service.request("1000");
            return response;
        }
    }
    public void evaluateState(){
        if(failureCount >= failureThreshold){
            if ((System.currentTimeMillis() - lastFailureTime) > retryTimePeriod) {
                //We have waited long enough and should try checking if service is up
                state = State.PARTIALLY_OPEN;
            }else{
                state= State.OPEN;
            }
        }else{
            //Everything is fine
            state= State.CLOSED;
        }
    }
}
