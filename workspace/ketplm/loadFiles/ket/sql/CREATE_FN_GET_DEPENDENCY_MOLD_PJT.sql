create or replace
FUNCTION FN_GET_DEPENDENCY_MOLD_PJT(v_task_oid IN VARCHAR2, v_task_plan_start_date IN VARCHAR2, v_pjt_oid IN VARCHAR2)
/*******************************************************************************
*  [PLM System 1차개선] 금형 Project Task의 선행 Task List를 가져옴
********************************************************************************
*  FUNCTION NAME
*      FN_GET_DEPENDENCY_MOLD_PJT
********************************************************************************
*  2013-07-08       BoLee     initial creation
*******************************************************************************/
RETURN VARCHAR2 IS
    task_list VARCHAR2(2000) := '';
    i BINARY_INTEGER := 0;

BEGIN
    FOR task_id_list IN (SELECT CASE WHEN t.planenddate >= v_task_plan_start_date
                                      THEN t.id || 'ss'
                                 ELSE t.id || ''
                                  END task_id
                            FROM taskdependencylink l
                                ,(SELECT rownum id
                                        ,t.ida3a4
                                        ,t.ida2a2
                                        ,s.planenddate
                                    FROM E3PSTask t
                                        ,MoldProject p
                                        ,extendscheduledata s
                                   WHERE t.ida3b4 = p.ida2a2
                                     AND t.ida3a4 = s.ida2a2
                                     AND p.ida2a2 = v_pjt_oid
                                   START WITH t.ida3parentreference = 0
                                 CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference
                                   ORDER SIBLINGS BY t.taskseq
                                 ) t
                           WHERE l.ida3b5 = t.ida2a2
                             AND l.ida3a5 = v_task_oid) LOOP
        i := i + 1;
        IF i = 1 THEN
            task_list := task_id_list.task_id;
        ELSE
            task_list := task_list || ';' || task_id_list.task_id;
        END IF;
    END LOOP;

    RETURN (task_list);

EXCEPTION
    WHEN OTHERS THEN
    RETURN '';

END FN_GET_DEPENDENCY_MOLD_PJT;