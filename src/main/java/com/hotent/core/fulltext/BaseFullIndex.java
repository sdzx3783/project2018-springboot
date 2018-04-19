package com.hotent.core.fulltext;

import com.hotent.core.fulltext.IOperator;
import com.hotent.core.page.PageBean;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

public class BaseFullIndex {
	private Log log = LogFactory.getLog(BaseFullIndex.class);
	private String indexDir = "E:/temp/index";
	private String pkName = "id";
	private Analyzer analyzer = new IKAnalyzer();
	private IOperator indexOperator;
	private int maxResult = 400;

	public void setMaxResult(int size) {
		this.maxResult = size;
	}

	public void setIndexDir(String dir) {
		this.indexDir = dir;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public void setIndexOperator(IOperator indexOperator) {
		this.indexOperator = indexOperator;
	}

	public void indexAll() throws IOException {
		FSDirectory fsDir = FSDirectory.open(new File(this.indexDir));
		RAMDirectory ramDir = new RAMDirectory(fsDir);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_33, this.analyzer);
		config.setOpenMode(OpenMode.CREATE);
		IndexWriter ramWrite = new IndexWriter(ramDir, config);
		this.indexOperator.addDocument(ramWrite);
		ramWrite.close();
		IndexWriterConfig fsConfig = new IndexWriterConfig(Version.LUCENE_33, this.analyzer);
		fsConfig.setOpenMode(OpenMode.CREATE);
		IndexWriter fsWriter = new IndexWriter(fsDir, fsConfig);
		fsWriter.addIndexes(new Directory[]{ramDir});
		fsWriter.commit();
		fsWriter.optimize();
		fsWriter.close();
		fsDir.close();
	}

	public void delById(String id) throws CorruptIndexException, IOException {
		File file = new File(this.indexDir);
		FSDirectory fsDir = FSDirectory.open(file);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_33, this.analyzer);
		config.setOpenMode(OpenMode.APPEND);
		IndexWriter write = new IndexWriter(fsDir, config);
		Term term = new Term(this.pkName, id);
		write.deleteDocuments(term);
		write.close();
	}

	public void updDoc(String pkId, Document doc) throws CorruptIndexException, LockObtainFailedException, IOException {
		File file = new File(this.indexDir);
		FSDirectory fsDir = FSDirectory.open(file);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_33, this.analyzer);
		config.setOpenMode(OpenMode.APPEND);
		IndexWriter write = new IndexWriter(fsDir, config);
		Term term = new Term(this.pkName, pkId);
		write.updateDocument(term, doc);
		write.close();
	}

	public void addDoc(Document doc) throws IOException {
		FSDirectory fsDir = FSDirectory.open(new File(this.indexDir));
		IndexWriterConfig fsConfig = new IndexWriterConfig(Version.LUCENE_33, this.analyzer);
		fsConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter fsWriter = new IndexWriter(fsDir, fsConfig);
		fsWriter.addDocument(doc);
		fsWriter.close();
	}

	public String heightLight(String q, String content)
			throws ParseException, IOException, InvalidTokenOffsetsException {
		QueryParser parser = new QueryParser(Version.LUCENE_30, "field", this.analyzer);
		Query query = parser.parse(q);
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color=\"red\">", "</font>");
		Highlighter highlight = new Highlighter(formatter, new QueryScorer(query));
		TokenStream tokens = this.analyzer.tokenStream("field", new StringReader(content));
		String str = highlight.getBestFragment(tokens, content);
		return str == null ? (content.length() > 50 ? content.substring(0, 50) : content) : str;
	}

	public List getPage(String q, int currentPage, int pageSize) {
		try {
			PageBean e = new PageBean(currentPage, pageSize);
			File file = new File(this.indexDir);
			FSDirectory fsdir = FSDirectory.open(file);
			RAMDirectory dir = new RAMDirectory(fsdir);
			IndexSearcher search = new IndexSearcher(dir);
			search.setSimilarity(new IKSimilarity());
			String[] fields = this.indexOperator.getFields();
			Query query = IKQueryParser.parseMultiField(fields, q);
			TopDocs hits = search.search(query, this.maxResult);
			int totalRecord = hits.totalHits;
			int amount = hits.scoreDocs.length;
			e.setTotalCount(totalRecord);
			if (hits == null) {
				return null;
			} else {
				int start = (currentPage - 1) * pageSize;
				int end = currentPage * pageSize;
				ArrayList list = new ArrayList();

				for (int i = 0; i < totalRecord; ++i) {
					if (i >= start && i < end) {
						Document doc = search.doc(hits.scoreDocs[i].doc);
						list.add(doc);
					}
				}

				return list;
			}
		} catch (Exception arg18) {
			this.log.error(arg18.getMessage());
			return null;
		}
	}
}