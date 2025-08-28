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
            String response = bot.chat("Calculate the sum of 24.5 and 17.3 using the calculator service");
            System.out.println("response1:" + response);
            
            response = bot.chat("使用计算器进行 32.5 减去 21.3");
            System.out.println("response4:" + response);
            
            response = bot.chat("使用计算器进行 32.5 乘以 21.3");
            System.out.println("response5:" + response);
            
            response = bot.chat("使用计算器进行 32.5 除以 21.3");
            System.out.println("response6:" + response);
            
            response = bot.chat("使用计算器进行 3 的 5 次方");
            System.out.println("response7:" + response);
            
            // 计算绝对值
            response = bot.chat("使用计算器进行 -32.5 的绝对值");
            System.out.println("response8:" + response);
            
            response = bot.chat("What's the square root of 144?");
            System.out.println("response2:" + response);
            
            response = bot.chat("Show me the help for the calculator service");
            System.out.println("response3:" + response);
        } finally {
            mcpClient.close();
        }
    }
}
