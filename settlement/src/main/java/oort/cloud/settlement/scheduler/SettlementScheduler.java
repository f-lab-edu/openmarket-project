package oort.cloud.settlement.scheduler;

//@Component
public class SettlementScheduler {
//    private final JobLauncher jobLauncher;
//    private final Job settlementJob;
//
//    public SettlementScheduler(JobLauncher jobLauncher, Job settlementJob) {
//        this.jobLauncher = jobLauncher;
//        this.settlementJob = settlementJob;
//    }
//
//    @Scheduled(fixedRate = 10000)
//    public void runSettlementJob(){
//        LocalDate localYesterday = LocalDate.now().minusDays(1);
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("timestamp", System.currentTimeMillis())
//                .addLocalDate("confirmedAt", localYesterday)
//                .addString("status", OrderItemStatus.CONFIRMED.name())
//                .toJobParameters();
//
//        try {
//            jobLauncher.run(settlementJob, jobParameters);
//        } catch (JobExecutionAlreadyRunningException e) {
//            throw new RuntimeException(e);
//        } catch (JobRestartException e) {
//            throw new RuntimeException(e);
//        } catch (JobInstanceAlreadyCompleteException e) {
//            throw new RuntimeException(e);
//        } catch (JobParametersInvalidException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
