package com.hotent.core.util.jsonobject.support;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import java.lang.reflect.Field;

public class SuperclassExclusionStrategy implements ExclusionStrategy {
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	public boolean shouldSkipField(FieldAttributes fieldAttributes) {
		String fieldName = fieldAttributes.getName();
		Class theClass = fieldAttributes.getDeclaringClass();
		return this.isFieldInSuperclass(theClass, fieldName);
	}

	private boolean isFieldInSuperclass(Class<?> subclass, String fieldName) {
		for (Class superclass = subclass.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
			Field field = this.getField(superclass, fieldName);
			if (field != null) {
				return true;
			}
		}

		return false;
	}

	private Field getField(Class<?> theClass, String fieldName) {
		try {
			return theClass.getDeclaredField(fieldName);
		} catch (Exception arg3) {
			return null;
		}
	}
}