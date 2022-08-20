package io.github.monomer.Polymers;

import io.github.monomer.Monomers.Monomer;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A generic class representing a sequence of Monomer objects.
 *
 * @author Elon Litman
 * @version 1.7
 * @see io.github.monomer.Monomers
 * @see SeqInterface
 */
public non-sealed class Seq<T extends Monomer> implements SeqInterface<T> {

    private T[] monomers;

    private int length;

    public Seq() {
        this(10);
    }

    // Define overloaded constructors
    // Initialize length of Seq
    @SuppressWarnings("unchecked")
    public Seq(int initLength) {
        if(initLength < 0) {
            throw new IllegalArgumentException("Illegal length: " + initLength);
        }
        this.monomers = (T[]) new Monomer[initLength];
    }

    // Instantiate Seq object by String of monomers
    @SuppressWarnings("unchecked")
    public Seq(String monomersStr) {
        this.monomers = (T[]) new Monomer[monomersStr.length()];
        for(int i=0; i<monomersStr.length(); i++) {
            elongate((T) new Monomer(monomersStr.charAt(i)));
        }
    }

    // Instantiate Seq object by a String array of monomers
    @SuppressWarnings("unchecked")
    public Seq(String[] monomersStringArr) {
        StringBuilder monomersStr = new StringBuilder();
        for (String s : monomersStringArr) {
            monomersStr.append(s);
        }
        this.monomers = (T[]) new Monomer[monomersStr.length()];
        monomersStr = new StringBuilder(String.valueOf(monomersStr));
        for(int i=0; i<monomersStr.length(); i++) {
            elongate((T) new Monomer(monomersStr.charAt(i)));
        }
    }

    // Instantiate Seq object by an array of Monomers
    @SuppressWarnings("unchecked")
    public Seq(T[] monomerArray) {
        this.monomers = (T[]) new Monomer[monomerArray.length];
        for (T monomer : monomerArray) {
            elongate((T) new Monomer(String.valueOf(monomer)));
        }
    }

    // Define methods
    @Override
    public boolean isEmpty() {
        return length() == 0;
    }

    @Override
    public int length() {
        return this.length;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int needCapacity) {
        if (needCapacity > monomers.length) {
            Monomer[] oldMonomers = this.monomers;
            int newSize = this.length * 2 + 1;
            this.monomers = (T[]) new Monomer[newSize];
            this.monomers = (T[]) Arrays.copyOf(oldMonomers, newSize);
        }
    }

    /**
     * A method for elongating a Seq object by adding another Monomer.
     *
     * @param monomer A Monomer object
     * @return A condition pertaining to whether the Seq was elongated successfully
     */
    @Override
    public boolean elongate(T monomer) {
        ensureCapacity(this.length + 1);
        monomers[this.length++] = monomer;
        return true;
    }

    private void checkRange(int index) {
        if (index < 0 || index >= (this.length + 1)) {
            throw new IllegalArgumentException("illegal index: " + index);
        }
    }

    @Override
    public boolean insert(int index, T monomer) {
        checkRange(index);
        ensureCapacity(this.length + 1);
        System.arraycopy(this.monomers, index, monomers, index + 1, length - index);
        monomers[index] = monomer;
        this.length++;
        return true;
    }

    @Override
    public void remove(T monomer) {
        if (monomer == null) {
            for (int i = 0; i < this.length; i++) {
                if (this.monomers[i] == null) {
                    fastRemove(i);
                    return;
                }
            }
        } else {
            for (int i = 0; i < this.length; i++) {
                if (monomer.equals(this.monomers[i])) {
                    fastRemove(i);
                    return;
                }
            }
        }
    }

    private void fastRemove(int index) {
        int movedNumber = this.length - index - 1;
        if (movedNumber > 0) {
            System.arraycopy(this.monomers, index + 1, this.monomers, index, movedNumber);
        }
        this.monomers[--this.length] = null;
    }

    @Override
    public T get(int index) {
        return this.monomers[index];
    }

    @Override
    public int indexOf(T monomer) {

        if (monomer == null) {
            for (int i = 0; i < this.monomers.length; i++) {
                if (this.monomers[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < this.monomers.length; i++) {
                if (monomer.equals(this.monomers[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void set(int index, T monomer) {
        checkRange(index);
        ensureCapacity(this.length + 1);
        this.monomers[index] = monomer;
    }

    @Override
    public boolean contains(T monomer) {
        if (monomer == null) {
            for (T e : this.monomers) {
                if (e == null) {
                    return true;
                }
            }
        } else {
            for (T e : this.monomers) {
                if (monomer.equals(e)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.length; i++) {
            this.monomers[i] = null;
        }
        this.length = 0;
    }

    @Override
    public T[] toArray() {
        return this.monomers;
    }

    /**
     * Serialize the instantiated sequence to an output stream.
     *
     * @param fileName A file name
     * @see Seq#deserialize(String)
     */
    public void serialize(String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Serialized sequence is saved in: " + fileName);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Deserialize a sequence from an input stream.
     *
     * @param file The absolute or relative directory of a Monomer Seq file
     * @return Seq
     * @see Seq#serialize(String)
     */
    public static Seq<? extends Monomer> deserialize(String file) {
        Seq<? extends Monomer> inputSeq = null;
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            inputSeq = (Seq<? extends Monomer>)(in.readObject());
            System.out.println("Seq object successfully deserialized.");
            in.close();
            fileIn.close();
            return inputSeq;
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Seq class not found.");
            c.printStackTrace();
        }
        System.out.println("Seq object unsuccessfully deserialized, returning null.");
        return inputSeq;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator<>();
    }

    @Override
    public String toString() {
        return "Seq{" +
                "Monomers=" + Arrays.toString(monomers) +
                ", Length=" + length +
                "}";
    }

    private class CustomIterator<T> implements Iterator<T> {

        private int current = 0;

        @Override
        public boolean hasNext() {
            return this.current < length();
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            return (T) monomers[current++];
        }
    }
}