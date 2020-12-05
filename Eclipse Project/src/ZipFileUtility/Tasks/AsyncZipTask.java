package ZipFileUtility.Tasks;

import ZipFileUtility.ProgressMonitor;
import ZipFileUtility.ZipException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public abstract class AsyncZipTask<T> {

    private final ProgressMonitor progressMonitor;
    private final boolean runInThread;
    private final ExecutorService executorService;

    public AsyncZipTask(AsyncTaskParameters asyncTaskParameters) {
        this.progressMonitor = asyncTaskParameters.progressMonitor;
        this.runInThread = asyncTaskParameters.runInThread;
        this.executorService = asyncTaskParameters.executorService;
    }

    public void execute(T taskParameters) throws ZipException {
        progressMonitor.fullReset();
        progressMonitor.setState(ProgressMonitor.State.BUSY);
        progressMonitor.setCurrentTask(getTask());

        if (runInThread) {
            long totalWorkToBeDone = calculateTotalWork(taskParameters);
            progressMonitor.setTotalWork(totalWorkToBeDone);

            executorService.execute(() -> {
                try {
                    performTaskWithErrorHandling(taskParameters, progressMonitor);
                } catch (ZipException ignored) {
                } finally {
                    executorService.shutdown();
                }
            });
        } else {
            performTaskWithErrorHandling(taskParameters, progressMonitor);
        }
    }

    private void performTaskWithErrorHandling(T taskParameters, ProgressMonitor progressMonitor) throws ZipException {
        try {
            executeTask(taskParameters, progressMonitor);
            progressMonitor.endProgressMonitor();
        } catch (ZipException e) {
            progressMonitor.endProgressMonitor(e);
            throw e;
        } catch (Exception e) {
            progressMonitor.endProgressMonitor(e);
            throw new ZipException(e);
        }
    }

    protected void verifyIfTaskIsCancelled() throws ZipException {
        if (!progressMonitor.isCancelAllTasks()) {
            return;
        }

        progressMonitor.setResult(ProgressMonitor.Result.CANCELLED);
        progressMonitor.setState(ProgressMonitor.State.READY);
        throw new ZipException("Task cancelled", ZipException.Type.TASK_CANCELLED_EXCEPTION);
    }

    protected abstract void executeTask(T taskParameters, ProgressMonitor progressMonitor) throws IOException;

    protected abstract long calculateTotalWork(T taskParameters) throws ZipException;

    protected abstract ProgressMonitor.Task getTask();

    public static class AsyncTaskParameters {
        private final ProgressMonitor progressMonitor;
        private final boolean runInThread;
        private final ExecutorService executorService;

        public AsyncTaskParameters(ExecutorService executorService, boolean runInThread, ProgressMonitor progressMonitor) {
            this.executorService = executorService;
            this.runInThread = runInThread;
            this.progressMonitor = progressMonitor;
        }
    }
}
