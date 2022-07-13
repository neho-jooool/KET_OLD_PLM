create or replace
FUNCTION FN_GET_DEPENDENCY_TASK(v_task_oid IN VARCHAR2, v_task_plan_start_date IN VARCHAR2)
/*******************************************************************************
*  [PLM System 1차개선] Task의 선행 Task List를 가져옴
********************************************************************************
*  FUNCTION NAME
*      FN_GET_DEPENDENCY_TASK
********************************************************************************
*  2013-07-08       BoLee     initial creation
*******************************************************************************/
RETURN VARCHAR2 IS
    task_list VARCHAR2(2000) := '';
    i BINARY_INTEGER := 0;
BEGIN
    FOR task_oid_list IN (SELECT CASE WHEN s.planenddate >= v_task_plan_start_date
                                      THEN t.ida2a2 || 'ss'
                                 ELSE t.ida2a2 || ''
                                  END task_oid
                            FROM taskdependencylink l
                                ,e3pstask t
                                ,extendscheduledata s
                           WHERE l.ida3b5 = t.ida2a2
                             AND t.ida3a4 = s.ida2a2
                             AND l.ida3a5 = v_task_oid) LOOP
        i := i + 1;
        IF i = 1 THEN
            task_list := task_oid_list.task_oid;
        ELSE
            task_list := task_list || ';' || task_oid_list.task_oid;
        END IF;
    END LOOP;
    RETURN (task_list);
EXCEPTION
    WHEN OTHERS THEN
    RETURN '';
END FN_GET_DEPENDENCY_TASK;