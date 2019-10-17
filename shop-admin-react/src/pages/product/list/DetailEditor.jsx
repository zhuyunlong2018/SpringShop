import React, { PureComponent } from 'react';
import SimpleMDEEditor from 'yt-simplemde-editor';
import marked from 'marked';
import { fileSrc } from '@/commons';
import ImagesUpload from '@/components/images-upload'
import 'yt-simplemde-editor/dist/style.css'
import 'font-awesome/css/font-awesome.css'

/**
 * 商品详情--编辑器
 */

export default class DetailEditor extends PureComponent {
    state = {
        value: '',
        uploadVisible: false,
    };

    renderMarkdown = text => {
        const html = marked(text, { breaks: true });
        if (/language-/.test(html)) {
            const container = document.createElement('div');
            container.innerHTML = html;
            return container.innerHTML;
        }
        return html;
    };


    /**
     * 处理选中图片后回调
     * @param {Object} images 
     */
    handelSelectImages(images) {
        if (images.length > 0) {
            //选中图片后进行后续处理
            images.forEach(image => {
                const { keywords } = image
                //要插入内容框的图片地址
                const imageText = `![${keywords}](${fileSrc(image).replace(/\\/g, "/")})`;
                //设定插入内容
                this.simplemde.options.insertTexts.image = ["\n", `${imageText}`]
                //首先在原光标位置插入一个图片地址
                this.simplemde.drawImage()
            });
        }
        this.setState({ uploadVisible: false })
    }

    render() {
        const { uploadVisible } = this.state;

        const editorProps = {
            value: this.state.value,
            getMdeInstance: simplemde => {
                this.simplemde = simplemde;
            },
            onChange: (value) => {
                this.setState({ value })
            },
            options: {
                // see https://github.com/sparksuite/simplemde-markdown-editor#configuration
                spellChecker: false,
                forceSync: true,
                autoDownloadFontAwesome: false,
                autosave: {
                    enabled: true,
                    delay: 5000,
                    uniqueId: 'article_content',
                },
                renderingConfig: {
                    // codeSyntaxHighlighting: true,
                },
                previewRender: this.renderMarkdown, // 自定义预览渲染
                tabSize: 4,
                toolbar: [
                    'bold',
                    'italic',
                    'heading',
                    '|',
                    'quote',
                    'table',
                    'horizontal-rule',
                    'unordered-list',
                    'ordered-list',
                    '|',
                    'link',
                    {
                        name: 'image',
                        action: (editor) => {
                            console.log(editor)
                            console.log(editor.toolbar[10].action)
                            this.setState({ uploadVisible: true })
                        },
                        className: 'fa fa-image',
                        title: '上传图片',
                    },
                    '|',
                    'side-by-side',
                    'fullscreen',
                    '|',
                    {
                        name: 'guide',
                        action() {
                            const win = window.open(
                                'https://github.com/riku/Markdown-Syntax-CN/blob/master/syntax.md',
                                '_blank',
                            );
                            if (win) {
                                // Browser has allowed it to be opened
                                win.focus();
                            }
                        },
                        className: 'fa fa-info-circle',
                        title: 'Markdown 语法！',
                    },
                ],
            },
        };

        return (
            <div>
                <SimpleMDEEditor {...editorProps} />
                <ImagesUpload visible={uploadVisible}
                    handleCancel={() => this.setState({ uploadVisible: false })}
                    handelSelectImages={images => this.handelSelectImages(images)} />
            </div>

        )
    }
}

