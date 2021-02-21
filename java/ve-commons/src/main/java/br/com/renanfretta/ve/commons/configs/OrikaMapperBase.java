package br.com.renanfretta.ve.commons.configs;

import java.io.Serializable;
import java.util.List;

import ma.glasnost.orika.MapperFacade;

public abstract class OrikaMapperBase {

	protected static MapperFacade mapperFacade;

	public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
		if (source == null)
			return null;

		return mapperFacade.mapAsList(source, destinationClass);
	}

	public <S, D extends Serializable> D map(S sourceObject, Class<D> destinationClass) {
		if (sourceObject == null)
			return null;

		validateObject(sourceObject, true);
		validateClass(destinationClass, false);
		return mapperFacade.map(sourceObject, destinationClass);
	}

	public <S, D extends Serializable> D map(S sourceObject, D destinationObject) {
		if (sourceObject == null)
			return null;

		validateObject(sourceObject, true);
		validateObject(destinationObject, false);

		// destinationObject = SerializationUtils.clone(destinationObject);
		mapperFacade.map(sourceObject, destinationObject);
		return destinationObject;
	}

	public Serializable mapObject(Serializable sourceObject, Class<? extends Serializable> destinationClass) {
		if (sourceObject == null)
			return null;

		validateObject(sourceObject, true);
		validateClass(destinationClass, false);
		return mapperFacade.map(sourceObject, destinationClass);
	}

	private <D extends Serializable> void validateClass(Class<D> clazz, boolean source) {
		if (!Serializable.class.isAssignableFrom(clazz))
			throw new RuntimeException(getPreffixSerializableError(source) + "Class deve implementar Serializable");
	}

	private <S> void validateObject(S object, boolean source) {
		if (object != null && !(object instanceof Serializable))
			throw new RuntimeException(getPreffixSerializableError(source) + "Object deve implementar Serializable");
	}

	private String getPreffixSerializableError(boolean source) {
		return source ? "Source" : "Destination";
	}

}
