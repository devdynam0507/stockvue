package org.vuestock.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.extensions.excel.RowMapper
import org.springframework.batch.extensions.excel.poi.PoiItemReader
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemStreamReader
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.vuestock.domain.stock.out.StockSectorRepository
import org.vuestock.domain.stock.Stock
import org.vuestock.domain.stock.StockSector
import org.vuestock.batch.dto.StockBatchDto
import javax.persistence.EntityManagerFactory

@Configuration
@EnableBatchProcessing
class BatchConfiguration {

    @Bean
    fun stockSectorWriter(entityManagerFactory: EntityManagerFactory): JpaItemWriter<StockSector> {
        val stockSectorWriter: JpaItemWriter<StockSector> = JpaItemWriter()
        stockSectorWriter.setEntityManagerFactory(entityManagerFactory)
        return stockSectorWriter
    }

    @Bean
    fun stockWriter(entityManagerFactory: EntityManagerFactory): JpaItemWriter<Stock> {
        val stockWriter: JpaItemWriter<Stock> = JpaItemWriter()
        stockWriter.setEntityManagerFactory(entityManagerFactory)
        return stockWriter
    }

    @Bean
    fun stockProcessor(stockSectorRepository: StockSectorRepository): ItemProcessor<StockBatchDto, Stock> {
        return ItemProcessor {
            val stock = Stock(it.id, it.standardCode, it.stockName, it.isEnterpriseCompany)
            if (it.sectorCode1 != "0") {
                stockSectorRepository.findById(it.sectorCode1).ifPresent { sector ->
                    stock.sector1 = sector
                }
            }
            if (it.sectorCode2 != "0") {
                stockSectorRepository.findById(it.sectorCode2).ifPresent { sector ->
                    stock.sector2 = sector
                }
            }
            if (it.sectorCode3 != "0") {
                stockSectorRepository.findById(it.sectorCode3).ifPresent { sector ->
                    stock.sector3 = sector
                }
            }
            stock
        }
    }

    @Bean(name = ["kospiStep"])
    fun kospiStep(kospiReader: ItemStreamReader<StockBatchDto>,
                  stepBuilder: StepBuilderFactory,
                  stockWriter: JpaItemWriter<Stock>,
                  stockProcessor: ItemProcessor<StockBatchDto, Stock>): Step {
        return stepBuilder.get("kospi_step")
                          .chunk<StockBatchDto, Stock>(10)
                          .reader(kospiReader)
                          .processor(stockProcessor)
                          .writer(stockWriter)
                          .build()
    }

    @Bean(name = ["kosdaqStep"])
    fun kosdaqStep(kosdaqReader: ItemStreamReader<StockBatchDto>,
                   stepBuilder: StepBuilderFactory,
                   stockWriter: JpaItemWriter<Stock>,
                   stockProcessor: ItemProcessor<StockBatchDto, Stock>): Step {
        return stepBuilder.get("kosdaq_step")
            .chunk<StockBatchDto, Stock>(10)
            .reader(kosdaqReader)
            .processor(stockProcessor)
            .writer(stockWriter)
            .build()
    }

    @Bean(name = ["stockSectorStep"])
    fun stockSectorStep(stockSectorWriter: JpaItemWriter<StockSector>,
                        stepBuilder: StepBuilderFactory,
                        stockSectorReader: ItemStreamReader<StockSector>): Step {
        return stepBuilder.get("stock_sector_step")
                          .chunk<StockSector, StockSector>(10)
                          .reader(stockSectorReader)
                          .writer(stockSectorWriter)
                          .build()
    }

    @Bean
    fun stockSectorJob(stockSectorStep: Step, jobBuilder: JobBuilderFactory): Job {
        return jobBuilder.get("stock_sector_job")
            .start(stockSectorStep)
            .build()
    }

    @Bean
    fun kospiJob(kospiStep: Step, jobBuilder: JobBuilderFactory): Job {
        return jobBuilder.get("kospi_job_")
                         .start(kospiStep)
                         .incrementer(RunIdIncrementer())
                         .build()
    }

    @Bean
    fun kosdaqJob(kosdaqStep: Step, jobBuilder: JobBuilderFactory): Job {
        return jobBuilder.get("kosdaq_job_")
                         .start(kosdaqStep)
                         .incrementer(RunIdIncrementer())
                         .build()
    }

    @Bean(name = ["kospiReader"])
    fun kospiExcelReader(stockRowMapper: RowMapper<StockBatchDto>): ItemStreamReader<StockBatchDto> {
        val poiItemReader: PoiItemReader<StockBatchDto> = PoiItemReader()
        poiItemReader.setResource(ClassPathResource("kospi_code.xlsx"))
        poiItemReader.setRowMapper(stockRowMapper)
        poiItemReader.setLinesToSkip(1)
        return poiItemReader
    }

    @Bean(name = ["kosdaqReader"])
    fun kosdaqExcelReader(stockRowMapper: RowMapper<StockBatchDto>): ItemStreamReader<StockBatchDto> {
        val poiItemReader: PoiItemReader<StockBatchDto> = PoiItemReader()
        poiItemReader.setResource(ClassPathResource("kosdaq_code.xlsx"))
        poiItemReader.setRowMapper(stockRowMapper)
        poiItemReader.setLinesToSkip(1)
        return poiItemReader
    }

    @Bean
    fun stockSectorReader(stockSectorRowMapper: RowMapper<StockSector>): ItemStreamReader<StockSector> {
        val poiItemReader: PoiItemReader<StockSector> = PoiItemReader()
        poiItemReader.setResource(ClassPathResource("idxcode.xlsx"))
        poiItemReader.setRowMapper(stockSectorRowMapper)
        poiItemReader.setLinesToSkip(1)
        return poiItemReader
    }
}