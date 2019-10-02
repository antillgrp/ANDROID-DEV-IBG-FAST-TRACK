package com.gerson.droidcafeadaptative.model;

import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.MapChangeRegistry;
import androidx.databinding.ObservableMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class
		MapToObservableMapAdapter<K, V, M extends Map<K,V>>
	implements
		ObservableMap<K, V>
{

	//region STATIC NEW MAPS
	public static LinkedHashMap NEW_LINKED_HASH_MAP(){ return new LinkedHashMap(); }

	public static ArrayMap NEW_ARRAY_MAP = new ArrayMap();

	public static HashMap NEW_HASH_MAP = new HashMap();

	public static TreeMap NEW_TREE_MAP = new TreeMap();
	//endregion

	private final M mMap;

	public MapToObservableMapAdapter(@NonNull M map){
		this.mMap = map;
	}

	//region ObservableMap Impl
	private transient MapChangeRegistry mListeners;

	@Override
	public void addOnMapChangedCallback(
		OnMapChangedCallback<? extends ObservableMap<K, V>, K, V> listener
	) {
		if (mListeners == null) {
			mListeners = new MapChangeRegistry();
		}
		mListeners.add(listener);
	}

	@Override
	public void removeOnMapChangedCallback(
		OnMapChangedCallback<? extends ObservableMap<K, V>, K, V> listener
	) {
		if (mListeners != null) {
			mListeners.remove(listener);
		}
	}

	private void notifyChange(Object key) {
		if (mListeners != null) {
			mListeners.notifyCallbacks(this, 0, key);
		}
	}
	//endregion

	//region Map Imp
	@Override
	public int size() { return this.mMap.size(); }

	@Override
	public boolean isEmpty() { return this.mMap.isEmpty(); }

	@Override
	public boolean containsKey(@Nullable Object key) { return this.mMap.containsKey(key); }

	@Override
	public boolean containsValue(@Nullable Object value) { return this.mMap.containsValue(value); }

	@Nullable
	@Override
	public V get(@Nullable Object key) { return this.mMap.get(key); }

	@Nullable
	@Override
	public V put(K key, V value)
	{
		V val = this.mMap.put(key, value);
		notifyChange(key);
		return val;
	}

	@Nullable
	@Override
	public V remove(@Nullable Object key)
	{
		V val = this.mMap.remove(key);
		notifyChange(key);
		return val;
	}

	@NonNull
	@Override
	public Set<Entry<K, V>> entrySet() { return this.mMap.entrySet(); }

	@Override
	public void putAll(@NonNull Map<? extends K, ? extends V> m)
	{
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
			this.put(e.getKey(), e.getValue());
	}

	@NonNull
	@Override
	public Set<K> keySet() { return this.mMap.keySet(); }

	@Override
	public void clear() { for (K key : this.keySet()) this.remove(key); }

	@NonNull
	@Override
	public Collection<V> values() { return this.mMap.values(); }

	@Nullable
	@Override
	public V getOrDefault(@Nullable Object key, @Nullable V defaultValue)
	{
		return this.mMap.getOrDefault(key, defaultValue);
	}

	@Override
	public void forEach(@NonNull BiConsumer<? super K, ? super V> action)
	{
		this.mMap.forEach(action);
	}

	@Override
	public void replaceAll(@NonNull BiFunction<? super K, ? super V, ? extends V> function)
	{
		this.mMap.replaceAll(function);
	}

	@Nullable
	@Override
	public V putIfAbsent(K key, V value) { return this.mMap.putIfAbsent(key, value); }

	@Override
	public boolean remove(@Nullable Object key, @Nullable Object value)
	{
		Object curValue = get(key);
		if
		(
			!Objects.equals(curValue, value)
			||
			(curValue == null && !containsKey(key))
		)
			return false;

		this.remove(key); //notifies
		return true;
	}

	@Override
	public boolean replace(K key, @Nullable V oldValue, V newValue)
	{
		Object curValue = get(key);
		if
		(
			!Objects.equals(curValue, oldValue)
			||
			(curValue == null && !containsKey(key))
		)
			return false;

		this.put(key, newValue); //notifies
		return true;
	}

	@Nullable
	@Override
	public V replace(K key, V value)
	{
		V curValue = get(key);
		this.replace(key, curValue, value); //notifies
		return curValue;
	}

	@Nullable
	@Override
	public V computeIfAbsent(K key, @NonNull Function<? super K, ? extends V> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		V v;
		if ((v = get(key)) == null) {
			V newValue;
			if ((newValue = mappingFunction.apply(key)) != null) {
				put(key, newValue); //notifies
				return newValue;
			}
		}
		return v;
	}

	@Nullable
	@Override
	public V computeIfPresent(K key, @NonNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		V oldValue;
		if ((oldValue = get(key)) != null) {
			V newValue = remappingFunction.apply(key, oldValue);
			if (newValue != null) {
				put(key, newValue); //notifies
				return newValue;
			} else {
				remove(key);
				return null;
			}
		} else {
			return null;
		}
	}

	@Nullable
	@Override
	public V compute(K key, @NonNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		V oldValue = get(key);

		V newValue = remappingFunction.apply(key, oldValue);
		if (newValue == null) {
			// delete mapping
			if (oldValue != null || containsKey(key)) {
				// something to remove
				remove(key); //notifies
				return null;
			} else {
				// nothing to do. Leave things as they were.
				return null;
			}
		} else {
			// add or replace old mapping
			put(key, newValue); //notifies
			return newValue;
		}
	}

	@Nullable
	@Override
	public V merge(K key, @NonNull V value, @NonNull BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		Objects.requireNonNull(value);
		V oldValue = get(key);
		V newValue = (oldValue == null) ? value :
			remappingFunction.apply(oldValue, value);
		if (newValue == null) {
			remove(key); //notifies
		} else {
			put(key, newValue); //notifies
		}
		return newValue;
	}
	//endregion



}