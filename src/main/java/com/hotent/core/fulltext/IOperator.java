package com.hotent.core.fulltext;

import org.apache.lucene.index.IndexWriter;

public interface IOperator {
	void addDocument(IndexWriter arg0);

	String[] getFields();
}