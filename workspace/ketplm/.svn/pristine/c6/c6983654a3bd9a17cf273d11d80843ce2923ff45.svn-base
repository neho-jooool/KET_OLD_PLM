package e3ps.groupware.board.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.pom.Transaction;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.groupware.board.Board;
import ext.ket.shared.log.Kogger;

public class BoardHelper implements wt.method.RemoteAccess, java.io.Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static BoardHelper manager = new BoardHelper();

    public String create(Map _map, Vector files)throws Exception{
        if(!SERVER) {
            Class argTypes[] = new Class[]{Map.class, Vector.class};
            Object args[] = new Object[]{_map, files};
            try {
                return (String)wt.method.RemoteMethodServer.getDefault().invoke(
                    "create",
                    null,
                    this,
                    argTypes,
                    args);
            }
            catch(RemoteException e) {
                Kogger.error(getClass(), e);
                throw new WTException(e);
            }
            catch(InvocationTargetException e) {
                Kogger.error(getClass(), e);
                throw new WTException(e);
            }
            catch(Exception e){
                Kogger.error(getClass(), e);
                throw e;
            }
        }

        KETParamMapUtil map = KETParamMapUtil.getMap(_map);
        Transaction trx = new Transaction();
        String oid = null;
        try {
            trx.start();

            Board b = Board.newBoard();
            b.setTitle(map.getString("title"));
            b.setWebEditor(map.getString("webEditor"));
            b.setWebEditorText(map.getString("webEditorText"));
            b.setPreferred(map.getInt("preferred"));
            b.setOwner(SessionHelper.manager.getPrincipalReference());
            b = (Board)PersistenceHelper.manager.save(b);
            b = (Board) E3PSContentHelper.service.attach(b, files);
            trx.commit();
            trx = null;
            oid = CommonUtil.getOIDString(b);
        } catch(Exception e) {
            throw e;
        } finally {
            if(trx!=null){
                trx.rollback();
            }
        }

        return oid;
    }

    public String delete(String oid){
        try{
            if(oid != null){
                ReferenceFactory f = new ReferenceFactory();
                Board b = (Board) f.getReference(oid).getObject();
                PersistenceHelper.manager.delete(b);
            }
        }catch(Exception e){
            Kogger.error(getClass(), e);
        }

        return oid;
    }

    public String modify(Map _map, Vector files)throws Exception{
        if(!SERVER) {
            Class argTypes[] = new Class[]{Map.class, Vector.class};
            Object args[] = new Object[]{_map, files};
            try {
                return (String)wt.method.RemoteMethodServer.getDefault().invoke(
                    "modify",
                    null,
                    this,
                    argTypes,
                    args);
            }
            catch(RemoteException e) {
                Kogger.error(getClass(), e);
                throw new WTException(e);
            }
            catch(InvocationTargetException e) {
                Kogger.error(getClass(), e);
                throw new WTException(e);
            }
            catch(Exception e){
                Kogger.error(getClass(), e);
                throw e;
            }
        }

        KETParamMapUtil map = KETParamMapUtil.getMap(_map);
        String oid = null;
        Transaction trx = new Transaction();
        try {
            trx.start();
            oid = map.getString("oid");
            ReferenceFactory f = new ReferenceFactory();
            if(oid != null){
                Board b = (Board) f.getReference(oid).getObject();
                b.setTitle(map.getString("title"));
                b.setWebEditor(map.getString("webEditor"));
                b.setWebEditorText(map.getString("webEditorText"));
                b.setPreferred(map.getInt("preferred"));
                b = (Board) PersistenceHelper.manager.modify(b);

                b = (Board) HelpBoardUtil.updateAttachFiles(b, map, files);
            }
            trx.commit();
            trx = null;
        } catch(Exception e) {
            throw e;
        } finally {
            if(trx!=null){
                trx.rollback();
            }
        }

        return oid;
    }

}
