/**
 * 
 */
package com.fwzs.master.modules.fwzs.service;

import com.fwzs.master.common.config.Global;
import com.fwzs.master.common.persistence.Page;
import com.fwzs.master.common.service.CrudService;
import com.fwzs.master.common.utils.FileUtils;
import com.fwzs.master.common.utils.IdGen;
import com.fwzs.master.modules.fwzs.dao.FwmFileDao;
import com.fwzs.master.modules.fwzs.dao.FwmFileStatisticsDao;
import com.fwzs.master.modules.fwzs.entity.BsProduct;
import com.fwzs.master.modules.fwzs.entity.EChart.*;
import com.fwzs.master.modules.fwzs.entity.FwmFile;
import com.fwzs.master.modules.fwzs.entity.FwmFileStatistics;
import com.fwzs.master.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 防伪码文件列表Service
 * @author ly
 * @version 2017-09-30
 */
@Service
@Transactional(readOnly = true)
public class FwmFileService extends CrudService<FwmFileDao, FwmFile> {

    @Autowired
    private FwmFileStatisticsDao fwmFileStatisticsDao;

	public FwmFile get(String id) {
		return super.get(id);
	}
	
	public List<FwmFile> findList(FwmFile fwmFile) {
		fwmFile.getSqlMap().put("dsf", dataScopeFilter(fwmFile.getCurrentUser(), "o", "u"));
		return super.findList(fwmFile);
	}
	
	public Page<FwmFile> findPage(Page<FwmFile> page, FwmFile fwmFile) {
		fwmFile.getSqlMap().put("dsf", dataScopeFilter(fwmFile.getCurrentUser(), "o", "u"));
		return super.findPage(page, fwmFile);
	}

    /**
     * 获取企业生码统计数据
     *
     * @param page
     * @param fwmFileStatistics
     * @return
     */
    public Page<FwmFileStatistics> findFwmFileStatistics(Page<FwmFileStatistics> page, FwmFileStatistics fwmFileStatistics) {
        if (fwmFileStatistics.getBeginDate() == null) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MONTH, -6);
            fwmFileStatistics.setBeginDate(now.getTime());
        }
        if (fwmFileStatistics.getEndDate() == null) {
            fwmFileStatistics.setEndDate(Calendar.getInstance().getTime());
        }

        fwmFileStatistics.setPage(page);
        if (UserUtils.isSystemManager()) {
            page.setList(fwmFileStatisticsDao.findList(fwmFileStatistics));
        } else {
            fwmFileStatistics.setCompany(UserUtils.getUser().getCompany());
            page.setList(fwmFileStatisticsDao.findListByCompany(fwmFileStatistics));
        }

        return page;
    }

    /**
     * 获取图表数据
     *
     * @param fwmFileStatistics
     * @return
     */
    public EChart getChartData(FwmFileStatistics fwmFileStatistics) {
        if (fwmFileStatistics.getBeginDate() == null) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MONTH, -6);
            fwmFileStatistics.setBeginDate(now.getTime());
        }
        if (fwmFileStatistics.getEndDate() == null) {
            fwmFileStatistics.setEndDate(Calendar.getInstance().getTime());
        }

        fwmFileStatistics.setCompany(UserUtils.getUser().getCompany());
        List<FwmFileStatistics> statisticsList = fwmFileStatisticsDao.findFwmFileStatistics(fwmFileStatistics);

        // 设置图表数据
        EChart echart = new EChart();
        List<String> legendList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(statisticsList)) {
            EChartSeries chartSeries = new EChartSeries();
            List<EChartData> chartDataList = new ArrayList<>();
            for (FwmFileStatistics statistics : statisticsList) {
                legendList.add(statistics.getProductName());
                chartDataList.add(new EChartData(statistics.getProductName(), statistics.getCodeCount().toString()));
            }
            chartSeries.setName("生码数量");
            chartDataList.get(0).setSelected(true);
            chartSeries.seteChartDataList(chartDataList);
            echart.setEchartSeries(chartSeries);
        }

        // 设置图表标题
        EChartTitle chartTitle = new EChartTitle();
        chartTitle.setLeft("center");
        chartTitle.setText("生码统计");
        chartTitle.setSubtext(DateFormatUtils.format(fwmFileStatistics.getBeginDate(), "yyyy-MM-dd") + " 到 "
                + DateFormatUtils.format(fwmFileStatistics.getEndDate(), "yyyy-MM-dd"));
        echart.setEchartTitle(chartTitle);

        // 设置图表图例
        EChartLegend chartLegend = new EChartLegend();
        chartLegend.setBottom(10);
        chartLegend.setLeft("center");
        chartLegend.setData(legendList);
        echart.setEchartLegend(chartLegend);

        return echart;
    }

    // 计算总的生码量
    public long caculateCodeCount(FwmFileStatistics fwmFileStatistics) {
        fwmFileStatistics.setPage(null);
        if (UserUtils.isSystemManager()) {
            return fwmFileStatisticsDao.findTotalCount(fwmFileStatistics);
        } else {
            return fwmFileStatisticsDao.findTotalCountByCompany(fwmFileStatistics);
        }
    }

    @Transactional(readOnly = false)
	public void save(FwmFile fwmFile) {
		super.save(fwmFile);
	}
	
	@Transactional(readOnly = false)
	public void delete(FwmFile fwmFile) {
		super.delete(fwmFile);
	}
	@Transactional(readOnly = false)
	public void save(FwmFile fwmFile,String fileUrl,String size) {
		
//		fwmFile.setFileSize(size);
		fwmFile.setFileUrl(fileUrl);
		save(fwmFile);
	}
	/**
	 * 生码
	 * 
	 * @return
	 */
	public List<String> genCode(BsProduct bsProduct,String num,String fileName) {
		int nums = Integer.parseInt(num);
		if (nums > 0) {
			StringBuffer codes = new StringBuffer("");
			List<String> list = new ArrayList<>();
			String preUrl=Global.getConfig("fwzs.preUrl");
			String radom = "";
			for (int i = 0; i < nums; i++) {
				//
				radom = IdGen.genFwm32(bsProduct);
				codes.append(preUrl+radom + System.getProperty("line.separator"));
//				codes.append(preUrl+radom + ";");
				list.add(radom);
			}
			// 保存文件
			FileUtils.writeToFile(fileName, codes.toString(), true);
			return list;
		}
		return null;
 
	}
}