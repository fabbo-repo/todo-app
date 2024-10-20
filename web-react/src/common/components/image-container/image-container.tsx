import React, { useRef, useState } from "react";
import { Camera } from "lucide-react";
import "./image-container.css";

interface ImageContainerProps {
  initialImageUrl: string;
  onImageChange?: (newImage: File) => void;
}

const ImageContainer: React.FC<ImageContainerProps> = ({
  initialImageUrl,
  onImageChange,
}) => {
  const [imageUrl, setImageUrl] = useState(initialImageUrl);

  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleImageClick = () => {
    fileInputRef.current?.click();
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      const file = e.target.files[0];
      const reader = new FileReader();
      reader.onloadend = () => {
        setImageUrl(reader.result as string);
      };
      reader.readAsDataURL(file);
      onImageChange?.(file);
    }
  };

  return (
    <>
      <div className="image-container" onClick={handleImageClick}>
        <img
          src={imageUrl}
          alt="Preview view"
          className="image-container-preview"
        />
        <div className="image-container-overlay">
          <Camera size={24} />
        </div>
      </div>
      {onImageChange && (
        <input
          type="file"
          ref={fileInputRef}
          style={{ display: "none" }}
          onChange={handleFileChange}
          accept="image/*"
        />
      )}
    </>
  );
};

export default ImageContainer;
