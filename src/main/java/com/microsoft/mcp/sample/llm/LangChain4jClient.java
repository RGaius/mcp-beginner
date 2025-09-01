package com.microsoft.mcp.sample.llm;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openaiofficial.OpenAiOfficialChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;

import java.time.Duration;
import java.util.List;

/**
 * @author gaius.zhao
 * @date 2025/8/28
 */
public class LangChain4jClient {
    
    public static void main(String[] args) throws Exception {        // Configure the
        ChatLanguageModel model = OpenAiOfficialChatModel.builder().apiKey(System.getenv("API_KEY"))
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1").modelName("qwen-max").build();
        
        // Create MCP transport for connecting to server
        McpTransport transport = new HttpMcpTransport.Builder().sseUrl("http://localhost:8080/sse")
                .timeout(Duration.ofSeconds(60)).logRequests(true).logResponses(true).build();
        
        // Create MCP client
        McpClient mcpClient = new DefaultMcpClient.Builder().transport(transport).build();
        
        // Create a tool provider that automatically discovers MCP tools
        ToolProvider toolProvider = McpToolProvider.builder().mcpClients(List.of(mcpClient)).build();
        
        // The MCP tool provider automatically handles:
        // - Listing available tools from the MCP server
        // - Converting MCP tool schemas to LangChain4j format
        // - Managing tool execution and responses
        Bot bot = AiServices.builder(Bot.class).chatLanguageModel(model).toolProvider(toolProvider).build();
        
        try {
            bot.chat("帮我创建属于 Huawei AX3 型号的硬件资产。其状态和子状态分别为库存和可用。");
        } finally {
            mcpClient.close();
        }
    }
}
