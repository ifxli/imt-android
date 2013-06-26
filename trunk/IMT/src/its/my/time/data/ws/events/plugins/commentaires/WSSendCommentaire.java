package its.my.time.data.ws.events.plugins.commentaires;

import its.my.time.data.bdd.base.BaseRepository;
import its.my.time.data.bdd.events.plugins.comment.CommentBean;
import its.my.time.data.bdd.events.plugins.comment.CommentRepository;
import its.my.time.data.ws.WSPostBase;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

public class WSSendCommentaire extends WSPostBase<CommentBean>{

	public WSSendCommentaire(Context context, CommentBean coment, PostCallback<CommentBean> callBack) {
		super(context, coment, callBack);
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public BaseRepository<CommentBean> getRepository() {
		return new CommentRepository(getContext());
	}
	
	@Override
	public List<NameValuePair> intitialiseParams(
			List<NameValuePair> nameValuePairs) {
		// TODO Auto-generated method stub
		return null;
	}

}
