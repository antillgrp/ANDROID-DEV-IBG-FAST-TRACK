package com.gerson.droidcafeadaptative.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableMap;
import androidx.lifecycle.LiveData;

import java.util.Objects;

/**
 * @param <K>
 * @param <V>
 * @param <M>
 */
public class
		ObservableMapToLiveDataAdapter<K ,	V,	M extends ObservableMap<K, V>>
	extends
		LiveData<M> {

	public ObservableMapToLiveDataAdapter(@NonNull M observableMap) {

		this.setValue(observableMap); //similar to super setvalue

		observableMap.addOnMapChangedCallback(
			new ObservableMap.OnMapChangedCallback<ObservableMap<K, V>, K, V>() {
				@Override
				public void onMapChanged(ObservableMap<K, V> sender, K key) {
					setValue(getValue()); //notifying LiveData observers
				}
			}
		);
	}

	@NonNull
	@Override
	public M getValue(){ return Objects.requireNonNull(super.getValue()); }
}
