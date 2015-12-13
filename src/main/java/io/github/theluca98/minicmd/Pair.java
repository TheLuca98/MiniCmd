package io.github.theluca98.minicmd;

class Pair<A, B> {

    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirstValue() {
        return first;
    }

    public B getSecondValue() {
        return second;
    }

}