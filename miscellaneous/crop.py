from PIL import Image
import os
path=os.getcwd()
flist=os.listdir()
os.mkdir('./crop')
for i in flist:
    if i[-3:] != 'png':
        continue
    image1 = Image.open(path+'/'+i)
    #image1.show()
    
    #이미지의 크기 출력
    #print(image1.size)
    
    # 이미지 자르기 crop함수 이용 ex. crop(left,up, rigth, down)
    croppedImage=image1.crop((959,0,2879,2159))
    
    #croppedImage.show()
    
    #print("잘려진 사진 크기 :",croppedImage.size)
    
    croppedImage.save('./crop/'+i[:-3]+'_crop.png')