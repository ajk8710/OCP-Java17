package ocpGuideBook.cha10;

import java.util.Optional;

public class Ch10Optional {
    
    public static void main(String[] args) {
        
        // Optional is clearer statement than returning null, saying there may not be a value.
        // It also allows to use functional programming style such as ifPresent(), instead of using if statement.
        // Spring jpaRepository findById is an example that returns optional.
        Optional<Double> opt = average(90, 100);
        if (opt.isPresent()) {
            System.out.println(opt.get());  // 95.0. NoSuchElementException if opt is empty.
        }
        
        Double avg = average().orElse(null);  // optional.orElse(other) returns optional.get() or other value if empty.
        System.out.println(avg);  // null
        
        String value = null;
        Optional<String> o = (value == null) ? Optional.empty() : Optional.of(value);
        o = Optional.ofNullable(value);  // This method does same thing as above. Returns optional of given value or empty optional if value is null.
        
        // ifPresent(Consumer c) calls consumer if present. Do nothing if empty optional. (If present, take it)
        // orElseGet(Supplier s) calls supplier if empty optional. (Get me something if empty)
        opt.orElseGet(() -> Math.random());
        
        // orElseThrow() throws NoSuchElementException
        // orElseThrow(Supplier s) throws exception created by supplier
        opt.orElseThrow(() -> new IllegalStateException());
        
    }
    
    // java.util.Optional is a box that contains something or nothing
    public static Optional<Double> average(int... scores) {  // Optional that contains double value, or no value.
        if (scores.length == 0) return Optional.empty();
        int sum = 0;
        for (int score: scores) sum += score;
        return Optional.of((double) sum / scores.length);
    }
    
}
