public class Driver {
    public static void main(String args[]){
        var service1= new WebService();
        var service1CircuitBreaker = new DefaultFoo(service1,3000, 2, 2000 * 1000 * 1000);

        var service2= new WebService();
        var service2CircuitBreaker= new DefaultFoo(service2, 3000, 2, 200*1000*1000);


    }
}
