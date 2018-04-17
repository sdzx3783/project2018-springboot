package com.hotent.core.web.query.scan;

import com.hotent.core.annotion.query.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

public class TableScaner {
	public static List<Class<?>> findTableScan(Resource[] basePackage) throws IOException, ClassNotFoundException {
		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
		ArrayList candidates = new ArrayList();
		if (basePackage == null) {
			return candidates;
		} else {
			Resource[] arr$ = basePackage;
			int len$ = basePackage.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				Resource resource = arr$[i$];
				if (resource.isReadable()) {
					MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
					if (isCandidate(metadataReader)) {
						candidates.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
					}
				}
			}

			return candidates;
		}
	}

	private static boolean isCandidate(MetadataReader metadataReader) throws ClassNotFoundException {
		try {
			Class e = Class.forName(metadataReader.getClassMetadata().getClassName());
			if (e.getAnnotation(Table.class) != null) {
				return true;
			}
		} catch (Throwable arg1) {
			;
		}

		return false;
	}
}