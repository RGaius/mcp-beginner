package com.microsoft.mcp.sample.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * 资产服务
 *
 * @author gaius.zhao
 * @date 2025/8/29
 */
@Service
@Slf4j
public class AssetService {
    
    /**
     * 创建资产台账数据
     *
     * @param assetType 资产类型；可选值：硬件资产、软件资产、云资产、耗材资产
     * @param model     资产规格型号，如设备型号、软件版本等
     * @param status    资产状态；如：在用、闲置、报废、维修中
     * @param subStatus 资产子状态；如：正常、故障、待处置等
     * @return 操作结果描述信息
     */
    @Tool(name = "资产创建", description = "创建特定类型的资产信息，用于资产台账管理")
    public String createAsset(String assetType, String model, String status, String subStatus) {
        log.info("创建资产台账数据, assetType: {}, model: {}, status: {}, subStatus: {}", assetType, model, status,
                subStatus);
        // 获取规格分类
        
        return "创建资产台账数据成功";
    }
}
