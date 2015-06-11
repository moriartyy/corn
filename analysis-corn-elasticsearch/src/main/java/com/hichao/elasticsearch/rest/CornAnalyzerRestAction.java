package com.hichao.elasticsearch.rest;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;

import com.hichao.analysis.corn.dic.Dictionary;

public class CornAnalyzerRestAction extends BaseRestHandler {

    @Inject
    protected CornAnalyzerRestAction(Settings settings, Client client, RestController controller) {
        super(settings, controller, client);

        controller.registerHandler(RestRequest.Method.GET, "/_ik/reload", this);
    }

    @Override
    protected void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {
        
        try {

            Dictionary.relaod();

            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("ok", true)
                    .endObject();

            channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));

        } catch (Exception e) {

            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("ok", false)
                    .field("reason", e.getMessage())
                    .endObject();

            channel.sendResponse(new BytesRestResponse(RestStatus.INTERNAL_SERVER_ERROR, builder));
        }
    }
}
