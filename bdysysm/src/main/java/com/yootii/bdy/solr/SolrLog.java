package com.yootii.bdy.solr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public class SolrLog {
	
	 private SolrClient createNewSolrClient(SolrInfo solrInfo) {
	        try {
	            System.out.println("server address:" + solrInfo.getSolrHome()+"bdylog");
	            HttpSolrClient client = new HttpSolrClient(solrInfo.getSolrHome()+"bdylog");
	            client.setConnectionTimeout(solrInfo.getSolrConnectTimeOut());
	            client.setDefaultMaxConnectionsPerHost(solrInfo.getSolrDefaultMaxConnect());
	            client.setMaxTotalConnections(solrInfo.getSolrMaxTotalConnect());
	            client.setSoTimeout(solrInfo.getSolrConnectTimeOut());
	            return client;
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }
	    }
	 public void createDocs(SolrInfo solrInfo,SolrData loglist) throws Exception {
	        System.out.println("======================add doc ===================");
	        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
	    	SolrClient client = createNewSolrClient(solrInfo);
	        try {
	        	docs = getSolrDocByLog(loglist);

	            UpdateResponse rsp = client.add(docs);
	            System.out
	                    .println("Add doc size" + docs.size() + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());

	            UpdateResponse rspcommit = client.commit();
	            System.out.println("commit doc to index" + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());

	        } catch (Exception e) {
	            e.printStackTrace();
	            throw e;
	        } finally {
	        	client.close();
	        }
	    }
	private Collection<SolrInputDocument> getSolrDocByLog(SolrData loglist) {
		  Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
	        
			List<Map<String, Object>> list = loglist.getDataTable();
			for (Map<String, Object> map:list) {
				SolrInputDocument doc = new SolrInputDocument();
				for (Map.Entry<String, Object> m : map.entrySet()) {
					//if (trademark.getKey().equals("tmImage") ) continue;
					doc.addField(m.getKey(), m.getValue());
				}
				docs.add(doc);			
			}
	        
	        return docs;
		
		
	}
}
