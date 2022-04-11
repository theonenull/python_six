from tkinter import scrolledtext, PhotoImage, Tk, Label, Button

win = Tk()
win.title("聊天室")
win.geometry('1200x600')
photo = PhotoImage(file='灯泡.png')
print(type(photo))
# 将图片放在主窗口的右边
lab = Label(win, image=photo, height=150, width=150, borderwidth=4, relief="sunken")
lab.place(x=0, y=0)
Name_room = Label(win, text="聊天室", bg="#7CCD7C", borderwidth=0,font=("隶书", 20))
Name_room.place(x=155, y=0, width=800, height=50)
scr = scrolledtext.ScrolledText(win, state='disabled',
                                font=("隶书", 18))
scr.place(x=155, y=50, width=800, height=400)
scr_massage = scrolledtext.ScrolledText(win, font=("隶书", 12))
scr_massage.place(x=155, width=800, y=450, height=150)
button_send = Button(text="发送")
button_send.place(x=800,y=540,width=100,height=50)
scr_member = scrolledtext.ScrolledText(win, font=("隶书", 18),state='disabled')
scr_member.place(x=955,y=0,width=245,height=600)
win.mainloop()
