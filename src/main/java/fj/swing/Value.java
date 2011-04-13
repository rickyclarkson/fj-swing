package fj.swing;

public interface Value<A> {
    A get();
    void set(A a);
    void addListener(Listener<A> listener);
}
