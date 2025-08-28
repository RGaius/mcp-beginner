package com.microsoft.mcp.sample.llm;

/**
 * @author gaius.zhao
 * @date 2025/8/28
 */
public interface Bot {
    
    /**
     * chat
     *
     * @param prompt
     * @return
     */
    String chat(String prompt);
}
