package com.goodcat.vkclient.application.adapter;


import android.content.Context;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.model.messages.MessagesModel;
import com.goodcat.vkclient.application.model.user.UserModel;
import com.goodcat.vkclient.application.service.MessageAttachment;
import com.goodcat.vkclient.application.service.DownloadImageService;

import java.util.List;

public class DialogMessageAdapter extends ArrayAdapter<MessagesModel> {

    List<UserModel> dialogUsers;
    List<MessagesModel> messages;
    private Context context;

    public DialogMessageAdapter(Context context,  List<MessagesModel> objects, List<UserModel> userModels) {
        super(context, 0, objects);
        this.dialogUsers = userModels;
        this.messages = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View currentView = View.inflate(getContext(), R.layout.dialog_messages_list_item, null);
        MessagesModel message = getItem(position);

        ImageButton userImage = (ImageButton) currentView.findViewById(R.id.user_image_dialog_message);
        TextView userMessage = (TextView) currentView.findViewById(R.id.user_text_dialog_message);
        TextView messageDate = (TextView) currentView.findViewById(R.id.date_dialog_message);
        LinearLayout contentWrapper = (LinearLayout) currentView.findViewById(R.id.dialogContentWrapper);

        for(UserModel um:this.dialogUsers){
            if(message.getFrom_id() == um.getId()) {
                DownloadImageService.loadImage(userImage, um.getPhoto50());
            } else if(message.getUser_id() == um.getId()){
                DownloadImageService.loadImage(userImage, um.getPhoto50());
            }
        }

        if(message.getAttachments() !=  null) {
            //Log.d("Mes attachment size", "" + message.getAttachments().size());
            MessageAttachment attachment = new MessageAttachment(getContext(),contentWrapper,message,dialogUsers);
            //Log.d("attachment BODY",""+message.getBody());
            attachment.printAttachment();
        }
        if(message.getEmoji()==1){
            addSmily(userMessage,message);
        } else {
            userMessage.setVisibility(View.VISIBLE);
            userMessage.setText(message.getBody() + "");
        }
        messageDate.setText(message.getDateToString()+"");

        return currentView;




    }

    public void addSmily(TextView myEditText,MessagesModel message) {
        CharSequence text = message.getBody();
        int resource  = R.drawable.emotion_smile;
        myEditText.setText(getSpannableText(text,resource));
    }

    private Spannable getSpannableText(CharSequence text, int smilyToAppend) {
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(text+" ");
        ImageSpan smilySpan = new ImageSpan(this.context,smilyToAppend);
        spannable.setSpan(smilySpan, spannable.length()-1, spannable.length(), 0);
        return spannable;
    }


   /* public CharSequence setEmojiText(String text) {
        //text = EmojiUtils.convertTag(text);
        CharSequence spanned = Html.fromHtml(text, emojiGetter, null);
        //setText(spanned);
        return spanned;
    }

    private Html.ImageGetter emojiGetter = new Html.ImageGetter()
    {
        public Drawable getDrawable(String source){
            int id = context.getResources().getIdentifier(source, "drawable", context.getPackageName());
            Drawable emoji = context.getResources().getDrawable(id);
            int w = (int)(emoji.getIntrinsicWidth() * 1.25);
            int h = (int)(emoji.getIntrinsicHeight() * 1.25);
            emoji.setBounds(0, 0, w, h);
            return emoji;
        }
    };*/

/*
    D83DDE0A

    D83DDE03

    D83DDE09

    D83DDE06

    D83DDE1C

    D83DDE0B

    D83DDE0D

    D83DDE0E

    D83DDE12

    D83DDE0F

    D83DDE14

    D83DDE22

    D83DDE2D

    D83DDE29

    D83DDE28

    D83DDE10

    D83DDE0C

    D83DDE04

    D83DDE07

    D83DDE30

    D83DDE32

    D83DDE33

    D83DDE37

    D83DDE02

    D83DDE1A

    D83DDE15

    D83DDE2F

    D83DDE26

    D83DDE35

    D83DDE20

    D83DDE21

    D83DDE1D

    D83DDE34

    D83DDE18

    D83DDE1F

    D83DDE2C

    D83DDE36

    D83DDE2A

    D83DDE2B

    D83DDE00

    D83DDE25

    D83DDE1B

    D83DDE16

    D83DDE24

    D83DDE23

    D83DDE27

    D83DDE11

    D83DDE05

    D83DDE2E

    D83DDE1E

    D83DDE19

    D83DDE13

    D83DDE01

    D83DDE31

    D83DDE08

    D83DDC7F

    D83DDC7D

    D83DDC4D

    D83DDC4E

    D83DDC4C

    D83DDC4F

    D83DDC4A

    D83DDE4F

    D83DDC43

    D83DDC46

    D83DDC47

    D83DDC48

    D83DDCAA

    D83DDC42

    D83DDC8B

    D83DDCA9

    D83CDF4A

    D83CDF77

    D83CDF78

    D83CDF85

    D83DDCA6

    D83DDC7A

    D83DDC28

    D83DDD1E

    D83DDC79

    D83CDF1F

    D83CDF4C

    D83CDF7A

    D83CDF7B

    D83CDF39

    D83CDF45

    D83CDF52

    D83CDF81

    D83CDF82

    D83CDF84

    D83CDFC1

    D83CDFC6

    D83DDC0E

    D83DDC0F

    D83DDC1C

    D83DDC2B

    D83DDC2E

    D83DDC03

    D83DDC3B

    D83DDC3C

    D83DDC05

    D83DDC13

    D83DDC18

    D83DDC94

    D83DDCAD

    D83DDC36

    D83DDC31

    D83DDC37

    D83DDC11

    D83CDF3A

    D83CDF3B

    D83CDF3C

    D83CDF3D

    D83CDF4B

    D83CDF4D

    D83CDF4E

    D83CDF4F

    D83CDF6D

    D83CDF37

    D83CDF38

    D83CDF46

    D83CDF49

    D83CDF50

    D83CDF51

    D83CDF53

    D83CDF54

    D83CDF55

    D83CDF56*/
}
