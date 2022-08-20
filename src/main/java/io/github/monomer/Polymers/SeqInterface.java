package io.github.monomer.Polymers;

import io.github.monomer.Monomers.Monomer;

import java.io.Serializable;

/**
 * An interface implemented by the Seq class. Abstract methods enforce classical ArrayList functionality.
 *
 * @author Elon Litman
 * @version 1.7
 * @param <T> Any monomer
 * @see Seq
 */
public sealed interface SeqInterface<T extends Monomer> extends Iterable<T>, Serializable permits Seq {

    boolean isEmpty();

    int length();

    boolean elongate(T monomer);

    boolean insert(int index, T monomer);

    void remove(T monomer);

    T get(int index);

    int indexOf(T monomer);

    void set(int index, T monomer);

    boolean contains(T monomer);

    void clear();

    T[] toArray();
}