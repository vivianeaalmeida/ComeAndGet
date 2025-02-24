export interface Tip {
  id: number;
  title: string;
  content: string;
  likeCount?: number;
  favoriteCount?: number;
  interactionId?: number;
  hasLiked?: boolean;
  hasFavorited?: boolean;
}
