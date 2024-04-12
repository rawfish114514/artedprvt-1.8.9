package com.artedprvt.std.impls.particle;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class UnionListView<O, E> extends ArrayList<O> {
    Map<String, List<E>> stringListMap;
    List<List<E>> listList;
    ReadWriteLock lock;
    Lock readLock;
    Lock writeLock;

    public UnionListView(List<O> defaultList) {
        super(defaultList);
        stringListMap = new HashMap<>();
        listList = new ArrayList<>();

        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    public void put(String name, List<E> list) {
        writeLock.lock();
        try {
            stringListMap.put(name, list);
            listList.add(list);
        } finally {
            writeLock.unlock();
        }
    }

    public List<E> get(String name) {
        readLock.lock();
        try {
            List<E> list = stringListMap.get(name);
            if (list == null) {
                return Collections.emptyList();
            }
            return list;
        } finally {
            readLock.unlock();
        }
    }

    public void remove(String name) {
        writeLock.lock();
        try {
            synchronized (this) {
                stringListMap.remove(name);
                listList.remove(get(name));
            }
        } finally {
            writeLock.unlock();
        }
    }


    public boolean add(String name, E e) {
        writeLock.lock();
        try {
            return stringListMap.get(name).add(e);
        } finally {
            writeLock.unlock();
        }
    }

    public int sumSize() {
        readLock.lock();
        try {
            int size = super.size();
            List<E>[] lists = new List[listList.size()];
            listList.toArray(lists);
            for (int i = 0; i < lists.length; i++) {
                size += lists[0].size();
            }
            return size;
        } finally {
            readLock.unlock();
        }
    }
}
