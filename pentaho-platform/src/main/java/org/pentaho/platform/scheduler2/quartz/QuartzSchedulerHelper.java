/*! ******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package org.pentaho.platform.scheduler2.quartz;

import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * Utility class for QuartzShceduler.
 *
 * @author Zhichun Wu
 */
public final class QuartzSchedulerHelper {
    static final String RESERVEDMAPKEY_PARAMETERS = "parameters";
    static final String RESERVEDMAPKEY_EXECPOLICY = "executionPolicy";

    static final String EXEC_POLICY_DEFAULT = "Unrestricted";
    static final String EXEC_POLICY_EXCLUSIVE = "Exclusive";

    static final String KETTLE_JOB_ACTIONID = "kjb.backgroundExecution";
    static final String KETTLE_TRANS_ACTIONID = "ktr.backgroundExecution";

    static QuartzJobKey extractJobKey(JobDetail jobDetail) {
        QuartzJobKey jobKey = null;
        try {
            jobKey = jobDetail == null ? null : QuartzJobKey.parse(jobDetail.getName());
        } catch (org.pentaho.platform.api.scheduler2.SchedulerException e) {
            // ignore error
        }

        return jobKey;
    }

    // http://stackoverflow.com/questions/19733981/quartz-skipping-duplicate-job-fires-scheduled-with-same-fire-time
    static void init(Scheduler scheduler) throws SchedulerException {
        if (scheduler == null) {
            return;
        }

        // attached listeners even the scheduler is shutted down
        // scheduler.addTriggerListener(ExclusiveKettleJobRule.instance);
    }

    // http://stackoverflow.com/questions/2676295/quartz-preventing-concurrent-instances-of-a-job-in-jobs-xml
    static void applyJobExecutionRules(Scheduler scheduler, JobDetail jobDetail) throws JobExecutionException {
        ExclusiveKettleJobRule.instance.applyRule(scheduler, jobDetail);
    }
}