package ocpGuideBook.cha12;

public class Ch12ServiceProviderConsumer {
    
    public static void main(String[] args) {
        
        /*
        Service is composed of an interface, any classes that interface references, and a way to look up implementations of the interface.
        
        Service provider interface & service locator can be in different modules or in a same module.
        Example of service provider interface, under zoo.tours.api package:
        
        public interface Tour {  // specifies behavior of service
            String name();
            int length();
            Souvenir getSouvenir();
        }
        
        module zoo.tours.api {  // module-info.java
            exports zoo.tours.api;  // exports package within module
        }
        
        
        Service locator can find any classes that implement the service provider interface.
        Java provides ServiceLoader class with static load() method to help finding implementing classes.
        load() method returns iterable, load().stream() method returns stream.
        
        public class TourFinder {  // under zoo.tours.reservations package
            public static List<Tour> findAllTours() {
                List<Tour> tours = new ArrayList<>();
                ServiceLoader<Tour> loader = ServiceLoader.load(Tour.class);
                for (Tour tour : loader) {
                    tours.add(tour);
                }
                return tours;
            }
        }
        
        module zoo.tours.reservations {  // module-info.java
            exports zoo.tours.reservations;
            requires zoo.tours.api;  // requires moduleName (of service interface), for compilation
            uses zoo.tours.api.Tour;  // uses InterfaceName (from the package), for lookup
        }
        
        
        Service provider is implementation of service provider interface.
        
        public class TourImpl implements Tour {  // under zoo.tours.agency package
            public String name() {
                return "Behind the Scenes";
            }
            public int length() {
                return 120;
            }
            public Souvenir getSouvenir() {
                return new Souvenir("stuffed animal");
            }
        }
        
        module zoo.tours.agency {  // Note it does not export zoo.tours.agency, instead it provides implementation.
            requires zoo.tours.api;  // requires moduleName (of service interface), for compilation
            provides zoo.tours.api.Tour with zoo.tours.agency.TourImpl;  // provides InterfaceName with ImplementationName
        }
        
        
        Consumer calls service locator, obtains and uses service.
        
        public class Tourist {  // under module zoo.visitor package
            public static void main(String[] args) {
                Tour tour = TourFinder.findSingleTour();
                System.out.println("Single tour: " + tour);
                List<Tour> tours = TourFinder.findAllTours();
                System.out.println("# tours: " + tours.size());
            }
        }
        
        module zoo.visitor {  // module-info.java
            requires zoo.tours.api;  // requires moduleName (of service interface), for compilation (Tour)
            requires zoo.tours.reservations;  // requires moduleName (of service locator), for compilation (TourFinder)
        }
        
        */
    }
    
}
